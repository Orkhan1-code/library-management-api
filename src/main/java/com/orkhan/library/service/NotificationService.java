package com.orkhan.library.service;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {
    CompletableFuture<Void> sendBookNotification(String recipient, String message);
}