package com.orkhan.library.service.impl;

import com.orkhan.library.config.FileStorageProperties;
import com.orkhan.library.security.FileContentValidator;
import com.orkhan.library.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("jpg", "jpeg", "png", "pdf");

    private final Path storageDirectory;
    private final FileStorageProperties properties;
    private final FileContentValidator fileContentValidator;

    public FileStorageServiceImpl(
            FileStorageProperties properties,
            FileContentValidator fileContentValidator) {

        this.properties = properties;
        this.fileContentValidator = fileContentValidator;

        this.storageDirectory = Path.of(properties.getLocation())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(storageDirectory);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not initialize file storage directory",
                    exception
            );
        }
    }

    @Override
    public String store(MultipartFile file) throws IOException {
        validateFile(file);

        String extension = getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + extension;

        Path destination = storageDirectory
                .resolve(filename)
                .normalize();

        if (!destination.getParent().equals(storageDirectory)) {
            throw new IOException("Invalid file path");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(
                    inputStream,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        return filename;
    }

    @Override
    public Path load(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Filename is required");
        }

        try {
            Path requestedFile = storageDirectory
                    .resolve(filename)
                    .normalize();

            if (!requestedFile.getParent().equals(storageDirectory)) {
                throw new IllegalArgumentException("Invalid filename");
            }

            return requestedFile;
        } catch (InvalidPathException exception) {
            throw new IllegalArgumentException(
                    "Invalid filename",
                    exception
            );
        }
    }

    @Override
    public void delete(String filename) throws IOException {
        Path file = load(filename);
        Files.deleteIfExists(file);
    }

    private void validateFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        if (file.getSize() > properties.getMaxSize()) {
            throw new IllegalArgumentException(
                    "File size exceeds the maximum allowed limit"
            );
        }

        String extension = getExtension(file.getOriginalFilename());

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "File type is not allowed"
            );
        }

        String contentType = file.getContentType();

        if (!isContentTypeAllowed(extension, contentType)) {
            throw new IllegalArgumentException(
                    "File content type does not match the file extension"
            );
        }

        fileContentValidator.validate(file, extension);
    }

    private String getExtension(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Filename is required");
        }

        String cleanName = Path.of(filename)
                .getFileName()
                .toString();

        int lastDot = cleanName.lastIndexOf('.');

        if (lastDot <= 0 || lastDot == cleanName.length() - 1) {
            throw new IllegalArgumentException(
                    "File must have a valid extension"
            );
        }

        return cleanName.substring(lastDot + 1)
                .toLowerCase(Locale.ROOT);
    }

    private boolean isContentTypeAllowed(
            String extension,
            String contentType) {

        if (contentType == null) {
            return false;
        }

        return switch (extension) {
            case "jpg", "jpeg" ->
                    contentType.equalsIgnoreCase("image/jpeg");

            case "png" ->
                    contentType.equalsIgnoreCase("image/png");

            case "pdf" ->
                    contentType.equalsIgnoreCase("application/pdf");

            default -> false;
        };
    }
}