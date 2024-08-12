package com.jian.family.business.attachment.task;

import com.jian.family.business.attachment.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class AutoRemoveAttachmentTask {

    @Autowired
    private AttachmentService service;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void remove() {
        log.debug("开始删除过期文件");
        service.removeExpired();
    }

}
