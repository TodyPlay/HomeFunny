package com.jian.family.business.attachment.api;

import com.jian.family.business.attachment.dto.AttachmentResponse;
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
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@RequestMapping("/api/attachment")
@RestController
public class AttachmentController {

    @Autowired
    private MinioAsyncClient minioAsyncClient;

    @GetMapping("download")
    public ResponseEntity<Resource> download(@RequestParam("obj") String object,
                                             @RequestHeader(value = HttpHeaders.RANGE, required = false) String range) throws Exception {

        var requestHeaders = new HashMap<String, String>();

        if (range != null) {
            requestHeaders.put(HttpHeaders.RANGE, range);
        }

        GetObjectResponse data = minioAsyncClient.getObject(GetObjectArgs.builder()
                        .object(object)
                        .bucket(Bucket.CHART_ROOM.getBucket())
                        .extraHeaders(requestHeaders)
                        .build())
                .exceptionally(ex -> null)
                .get();

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        Headers dataHeaders = data.headers();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(object, StandardCharsets.UTF_8).build());
        httpHeaders.set(HttpHeaders.ACCEPT_RANGES, dataHeaders.get(HttpHeaders.ACCEPT_RANGES));
        httpHeaders.set(HttpHeaders.CONTENT_RANGE, dataHeaders.get(HttpHeaders.CONTENT_RANGE));
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, dataHeaders.get(HttpHeaders.CONTENT_TYPE));
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, dataHeaders.get(HttpHeaders.CONTENT_LENGTH));

        return new ResponseEntity<>(new InputStreamResource(data), httpHeaders, range == null ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT);
    }

    @PostMapping("upload")
    public List<AttachmentResponse> upload(@RequestParam("files") MultipartFile[] files) throws Exception {

        var futures = new ArrayList<Future<AttachmentResponse>>(files.length);

        for (MultipartFile file : files) {
            var future = minioAsyncClient.putObject(PutObjectArgs.builder()
                            .bucket(Bucket.CHART_ROOM.getBucket())
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), 0)
                            .object(file.getOriginalFilename())
                            .build())
                    .thenApply(resp -> {
                        return AttachmentResponse.builder()
                                .id(resp.object())
                                .name(file.getOriginalFilename())
                                .success(true)
                                .build();
                    })
                    .exceptionally(ex -> {
                        log.debug(ex.getMessage(), ex);
                        return AttachmentResponse.builder()
                                .name(file.getOriginalFilename())
                                .success(false)
                                .exceptionMessage(ex.getMessage()).build();
                    });
            futures.add(future);
        }

        var result = new ArrayList<AttachmentResponse>(files.length);

        for (Future<AttachmentResponse> future : futures) {
            result.add(future.get());
        }

        return result;

    }
}
