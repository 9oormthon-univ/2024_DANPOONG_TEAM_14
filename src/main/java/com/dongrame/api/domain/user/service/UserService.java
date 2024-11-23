package com.dongrame.api.domain.user.service;

import com.dongrame.api.domain.user.dao.UserRepository;
import com.dongrame.api.domain.user.dto.UserResponseDto;
import com.dongrame.api.domain.user.dto.UserUpdateRequestDto;
import com.dongrame.api.domain.user.entity.User;
import com.dongrame.api.domain.user.entity.UserType;
import com.dongrame.api.global.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public Long save(String kakaoId, String nickname, String profileImage, String email) {

        Optional<User> existingUser = userRepository.findByKakaoIdAndActiveFalse(kakaoId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setActive(true);
            user.setNickname(nickname);
            user.setProfileImage(profileImage);
            user.setLevel(1);
            user.setEmail(email);
            return userRepository.save(user).getId();
        }

        User newUser = User.builder()
                .active(true)
                .kakaoId(kakaoId)
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .level(1)
                .build();

        return userRepository.save(newUser).getId();
    }

    @Transactional
    public Long updateUserType(UserType type) {
        User user = getCurrentUser();
        user.updateUserType(type);
        return user.getId();
    }

    @Transactional
    public Long updateUser(UserUpdateRequestDto dto) {
        User user = getCurrentUser();
        user.update(dto);
        return user.getId();
    }

    @Transactional
    public String updateProfileImage(MultipartFile profileImage) {
        try {
            User currentUser = getCurrentUser();
            String uploadDir = System.getProperty("user.home") + "/uploads/profileImages";
            Path directory = Paths.get(uploadDir);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            String originalFilename = profileImage.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = "profile_" + currentUser.getKakaoId() + "_" + System.currentTimeMillis() + extension;
            Path filePath = directory.resolve(newFilename);
            profileImage.transferTo(filePath.toFile());

            String profileImagePath = "/uploads/profileImages/" + newFilename;
            currentUser.updateProfileImage(profileImagePath);

            return profileImagePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile image", e);
        }
    }

    @Transactional
    public Long deleteUser() {
        User user = getCurrentUser();
        user.setActive(false);
        SecurityContextHolder.clearContext();
        refreshTokenService.deleteRefreshToken(user.getKakaoId());
        return user.getId();
    }

    public User findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId).orElse(null);
    }

    public UserResponseDto getCurrentLoginUser() {
        User user = getCurrentUser();
        return UserResponseDto.toDto(user);
    }

    public User getCurrentUser() {
        String kakaoId = getCurrentUserId();
        return userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OAuth2User) {
            return String.valueOf(((OAuth2User) principal).getAttributes().get("id"));
        } else {
            return principal.toString();
        }
    }
}
