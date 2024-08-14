package com.jian.family.business.attachment.api;

import com.jian.family.business.attachment.dto.AttachmentEntityDto;
import com.jian.family.business.attachment.dto.AttachmentListQuery;
import com.jian.family.business.attachment.dto.AttachmentUploadResponse;
import com.jian.family.business.attachment.entity.AttachmentEntity;
import com.jian.family.business.attachment.service.AttachmentService;
import com.jian.family.config.minio.Bucket;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioAsyncClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Future;

@Slf4j
@RequestMapping("/api/attachment")
@RestController
public class AttachmentController {

    @Autowired
    private MinioAsyncClient minioAsyncClient;

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("download")
    public ResponseEntity<Resource> download(@RequestParam
                                             Long id,
                                             @RequestHeader(value = HttpHeaders.RANGE, required = false)
                                             String range) throws Exception {

        var requestHeaders = new HashMap<String, String>();

        if (range != null) {
            requestHeaders.put(HttpHeaders.RANGE, range);
        }

        Optional<AttachmentEntity> opt = attachmentService.findById(id);

        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AttachmentEntity entity = opt.get();


        GetObjectResponse data = minioAsyncClient.getObject(GetObjectArgs.builder()
                        .object(entity.getObject())
                        .bucket(entity.getBucket())
                        .extraHeaders(requestHeaders)
                        .build())
                .exceptionally(ex -> null)
                .get();

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        Headers dataHeaders = data.headers();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(entity.getName(), StandardCharsets.UTF_8).build());
        httpHeaders.set(HttpHeaders.ACCEPT_RANGES, dataHeaders.get(HttpHeaders.ACCEPT_RANGES));
        httpHeaders.set(HttpHeaders.CONTENT_RANGE, dataHeaders.get(HttpHeaders.CONTENT_RANGE));
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, dataHeaders.get(HttpHeaders.CONTENT_TYPE));
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, dataHeaders.get(HttpHeaders.CONTENT_LENGTH));

        return new ResponseEntity<>(new InputStreamResource(data), httpHeaders, range == null ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT);
    }

    @PostMapping("upload")
    public List<AttachmentUploadResponse> upload(@RequestParam("files[]") MultipartFile[] files) throws Exception {

        var futures = new ArrayList<Future<AttachmentUploadResponse>>(files.length);

        for (MultipartFile file : files) {

            var name = file.getOriginalFilename();
            var bucket = Bucket.CHART_ROOM.getBucket();
            var object = UUID.randomUUID().toString();
            AttachmentEntity entity = attachmentService.save(name, bucket, object);

            var future = minioAsyncClient.putObject(PutObjectArgs.builder()
                            .bucket(bucket)
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), 0)
                            .object(object)
                            .build())
                    .thenApply(resp -> {
                        return AttachmentUploadResponse.builder()
                                .id(entity.getId())
                                .name(name)
                                .success(true)
                                .build();
                    })
                    .exceptionally(ex -> {
                        log.debug(ex.getMessage(), ex);
                        return AttachmentUploadResponse.builder()
                                .name(file.getOriginalFilename())
                                .success(false)
                                .exceptionMessage(ex.getMessage()).build();
                    });
            futures.add(future);
        }

        var result = new ArrayList<AttachmentUploadResponse>(files.length);

        for (var future : futures) {
            result.add(future.get());
        }

        return result;

    }

    @PostMapping("list")
    public PagedModel<AttachmentEntityDto> list(@RequestBody @Validated
                                                AttachmentListQuery request,
                                                Pageable pageable) {
        return new PagedModel<>(attachmentService.findAllByCondition(request, pageable));
    }

    @DeleteMapping("remove")
    public void remove(@RequestParam Long id) {
        attachmentService.deleteById(id);
    }
}
