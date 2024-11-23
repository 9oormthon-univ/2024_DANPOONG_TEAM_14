package com.dongrame.api.domain.file.service;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final String uploadDir = "/uploads";

    public ResponseEntity<UrlResource> getFile(String fileUrl) {
        try {
            Path filePath = Paths.get(fileUrl).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build(); // 파일이 없을 경우 404 반환
            }

            String contentType = "application/octet-stream"; // 기본값
            if (fileUrl.endsWith(".jpg") || fileUrl.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (fileUrl.endsWith(".png")) {
                contentType = "image/png";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new RuntimeException("파일을 읽을 수 없습니다.");
        }
    }
}
