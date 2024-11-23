package com.dongrame.api.domain.file.api;

import com.dongrame.api.domain.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@Tag(name = "file", description = "파일 조회")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "이미지 조회", description = "이미지를 조회합니다.")
    @GetMapping("/get")
    public ResponseEntity<UrlResource> getFile(@RequestParam String fileUrl) {
        return fileService.getFile(fileUrl);
    }
}
