package com.dongrame.api.domain.review.service;

import com.dongrame.api.domain.review.dao.ReviewImagRepository;
import com.dongrame.api.domain.review.dao.ReviewRepository;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewImag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewImagService {
    private final ReviewRepository reviewRepository;
    private final ReviewImagRepository imagRepository;


    @Transactional
    public void deleteReviewImages(Long reviewId) {
        List<ReviewImag> imageUrls = imagRepository.findByReviewId(reviewId);
        for (ReviewImag reviewImag : imageUrls) {
            // 이미지 파일 삭제
            File fileToDelete = new File(reviewImag.getImageUrl());
            if (fileToDelete.exists()) {
                boolean deleted = fileToDelete.delete();
                if (!deleted) {
                    throw new RuntimeException("파일 삭제 실패: " + reviewImag.getImageUrl());
                }
            }

            // DB에서 이미지 정보 삭제
            imagRepository.delete(reviewImag);
        }
    }

    @Transactional
    public void saveReviewImag(Long reviewId, List<MultipartFile> images) {
        if (images.size() > 3) {
            throw new RuntimeException("크기 초과");
        }

        String uploadDir = System.getProperty("user.home") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean dirCreated = dir.mkdirs(); // 디렉토리 생성 시 반환 값을 확인
            if (!dirCreated) {
                throw new RuntimeException("디렉토리 생성 실패"); // 디렉토리 생성 실패 시 예외 처리
            }
        }

        for (MultipartFile image : images) {
            String safeFileName = generateSafeFileName(reviewId, image);
            File savedFile = saveImageFile(uploadDir, safeFileName, image);

            saveImageInfoToDatabase(savedFile, reviewId);
        }
    }

    private String generateSafeFileName(Long reviewId, MultipartFile image) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String originalFilename = image.getOriginalFilename();

        if (originalFilename == null) {
            throw new IllegalArgumentException("파일 이름이 null입니다."); // 예외 처리 추가
        }

        return reviewId + "_" + timestamp + "_" + originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }


    private File saveImageFile(String uploadDir, String safeFileName, MultipartFile image) {
        File savedFile = new File(uploadDir + safeFileName);
        try {
            image.transferTo(savedFile);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image file: " + e.getMessage());
        }
        return savedFile;
    }

    private void saveImageInfoToDatabase(File savedFile, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없습니다"));

        ReviewImag reviewImag = ReviewImag.builder()
                .imageUrl(savedFile.getAbsolutePath())
                .review(review)
                .build();

        imagRepository.save(reviewImag);
    }
}
