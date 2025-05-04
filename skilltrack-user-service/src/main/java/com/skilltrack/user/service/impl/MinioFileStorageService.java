package com.skilltrack.user.service.impl;

import com.skilltrack.user.config.props.MinioProperties;
import com.skilltrack.user.exception.FileStorageException;
import com.skilltrack.user.service.FileStorageService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioFileStorageService implements FileStorageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) {
        validateFile(file);
        ensureBucketExists();

        String filename = generateFilename(file);

        try (InputStream inputStream = file.getInputStream()) {
            uploadFile(inputStream, filename, file.getContentType());
            log.info("Successfully uploaded file: {}", filename);
            return filename;
        } catch (IOException e) {
            log.error("Failed to upload file: {}", e.getMessage());
            throw new FileStorageException("Failed to upload file", e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(filename)
                            .build());
            log.info("Successfully deleted file: {}", filename);
        } catch (Exception e) {
            log.error("Failed to delete file: {}", filename, e);
            throw new FileStorageException("Failed to delete file: " + filename, e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("File must not be empty");
        }

        if (file.getOriginalFilename() == null) {
            throw new FileStorageException("File must have a name");
        }
    }

    private void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .build());

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(minioProperties.getBucket())
                                .build());
                log.info("Created bucket: {}", minioProperties.getBucket());
            }
        } catch (Exception e) {
            log.error("Failed to check/create bucket", e);
            throw new FileStorageException("Failed to create bucket", e);
        }
    }

    private String generateFilename(MultipartFile file) {
        String extension = extractFileExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + extension;
        log.debug("Generated filename: {} for original file: {}", filename, file.getOriginalFilename());
        return filename;
    }

    private String extractFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new FileStorageException("Invalid file name or missing extension");
        }

        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private void uploadFile(InputStream inputStream, String filename, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .object(filename)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());
        } catch (Exception e) {
            log.error("Failed to upload file to MinIO", e);
            throw new FileStorageException("Failed to upload file to storage", e);
        }
    }
}