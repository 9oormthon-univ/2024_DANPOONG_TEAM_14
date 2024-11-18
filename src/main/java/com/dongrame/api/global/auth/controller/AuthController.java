package com.dongrame.api.global.auth.controller;

import com.dongrame.api.global.auth.jwt.JwtTokenProvider;
import com.dongrame.api.global.auth.service.RefreshTokenService;
import com.dongrame.api.global.config.UrlProperties;
import com.dongrame.api.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
@Tag(name = "Auth", description = "인증/인가")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UrlProperties urlProperties;

    @Operation(summary = "액세스 토큰 재발급", description = "Cookie를 통해 액세스 토큰 발급 가능")
    @PostMapping("/reissue")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getRefreshTokenFromCookies(request);
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Refresh Token provided");
        }

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or Expired Refresh Token");
        }

        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        String savedRefreshToken = refreshTokenService.getRefreshToken(userId);
        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token mismatch");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(userId);
        CookieUtil.addAccessTokenToCookie(response, newAccessToken, jwtTokenProvider, urlProperties);

        return ResponseEntity.ok("Access Token reissued successfully");
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = CookieUtil.getRefreshTokenFromCookies(request);

        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {

            String kakaoId = jwtTokenProvider.getUserIdFromToken(accessToken);
            refreshTokenService.deleteRefreshToken(kakaoId);

            CookieUtil.deleteCookie(response, "AccessToken", urlProperties);
            CookieUtil.deleteCookie(response, "RefreshToken", urlProperties);

            return ResponseEntity.ok("Successfully logged out");
        }

        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid token");
    }
}
