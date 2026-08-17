package com.orkhan.library.security;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
public class FileContentValidator {

    public void validate(MultipartFile file, String extension) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            byte[] header = inputStream.readNBytes(8);

            if (!hasValidSignature(header, extension)) {
                throw new IllegalArgumentException("File content does not match the declared file type");
            }
        }
    }

    private boolean hasValidSignature(byte[] header, String extension) {
        return switch (extension) {
            case "jpg", "jpeg" -> isJpeg(header);
            case "png" -> isPng(header);
            case "pdf" -> isPdf(header);
            default -> false;
        };
    }

    private boolean isJpeg(byte[] header) {
        return header.length >= 3
                && (header[0] & 0xFF) == 0xFF
                && (header[1] & 0xFF) == 0xD8
                && (header[2] & 0xFF) == 0xFF;
    }

    private boolean isPng(byte[] header) {
        byte[] signature = {
                (byte) 0x89, 0x50, 0x4E, 0x47,
                0x0D, 0x0A, 0x1A, 0x0A
        };

        return Arrays.equals(header, signature);
    }

    private boolean isPdf(byte[] header) {
        return header.length >= 4
                && header[0] == 0x25
                && header[1] == 0x50
                && header[2] == 0x44
                && header[3] == 0x46;
    }
}