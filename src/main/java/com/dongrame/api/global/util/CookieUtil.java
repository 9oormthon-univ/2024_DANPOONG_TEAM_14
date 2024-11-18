package com.dongrame.api.global.util;

import com.dongrame.api.global.auth.jwt.JwtTokenProvider;
import com.dongrame.api.global.config.UrlProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static String getAccessTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("AccessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("RefreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void addAccessTokenToCookie(HttpServletResponse response, String accessToken, JwtTokenProvider jwtTokenProvider, UrlProperties urlProperties) {
        ResponseCookie cookie = ResponseCookie.from("AccessToken", accessToken)
                .maxAge((int) jwtTokenProvider.getValidityInMilliseconds() / 1000)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .domain(urlProperties.getDomain())
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void deleteCookie(HttpServletResponse response, String cookieName, UrlProperties urlProperties) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .domain(urlProperties.getDomain())
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
