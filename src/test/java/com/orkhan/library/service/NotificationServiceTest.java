package com.orkhan.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {"jwt.secret=test-secret-for-tests-only", "JWT_SECRET=test-secret-for-tests-only"})
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    void shouldRunNotificationAsynchronously() throws Exception {

        CompletableFuture<Void> notification = notificationService.sendBookNotification("test@example.com", "Test notification");
        assertNotNull(notification);
        assertFalse(notification.isDone());
        notification.get();
    }
}