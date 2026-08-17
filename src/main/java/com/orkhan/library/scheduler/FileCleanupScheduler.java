package com.orkhan.library.scheduler;

import com.orkhan.library.config.FileStorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Component
public class FileCleanupScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(FileCleanupScheduler.class);

    private final FileStorageProperties storageProperties;

    public FileCleanupScheduler(FileStorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Scheduled(fixedDelayString = "${scheduler.file-cleanup.interval}")
    public void removeOldFiles() {

        Path storageDirectory = Path.of(storageProperties.getLocation())
                .toAbsolutePath()
                .normalize();

        if (!Files.exists(storageDirectory)) {
            return;
        }

        Instant cutoffTime = Instant.now().minus(Duration.ofMillis(86_400_000));

        try (Stream<Path> files = Files.list(storageDirectory)) {

            files.filter(Files::isRegularFile).forEach(file -> deleteIfExpired(file, cutoffTime));

        } catch (IOException exception) {
            logger.error("Unable to scan file storage directory", exception);
        }
    }

    private void deleteIfExpired(Path file, Instant cutoffTime) {

        try {
            FileTime lastModified = Files.getLastModifiedTime(file);

            if (lastModified.toInstant().isBefore(cutoffTime)) {
                Files.deleteIfExists(file);

                logger.info( "Removed expired uploaded file: {}", file.getFileName());
            }

        } catch (IOException exception) {
            logger.warn("Could not remove uploaded file: {}", file.getFileName(), exception);
        }
    }
}