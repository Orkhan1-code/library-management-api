package com.orkhan.library.service.impl;

import com.orkhan.library.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    @Async
    public CompletableFuture<Void> sendBookNotification(String recipient, String message) {
        logger.info("Preparing notification for {}", recipient);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            logger.warn("Notification was interrupted for {}", recipient);
            return CompletableFuture.failedFuture(exception);
        }
        logger.info("Notification sent to {}: {}", recipient, message);
        return CompletableFuture.completedFuture(null);
    }
}