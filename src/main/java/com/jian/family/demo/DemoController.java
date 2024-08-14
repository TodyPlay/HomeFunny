package com.jian.family.demo;

import com.jian.family.business.attachment.dto.AttachmentUploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/demo")
@RestController
public class DemoController {

    @GetMapping("403")
    public ResponseEntity<?> _403() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("401")
    public ResponseEntity<?> _401() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("200")
    public AttachmentUploadResponse _200() {
        return AttachmentUploadResponse.builder().name("jian").exceptionMessage("except").success(false).build();
    }
}
