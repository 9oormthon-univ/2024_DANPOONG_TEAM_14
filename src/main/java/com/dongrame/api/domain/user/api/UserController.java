package com.dongrame.api.domain.user.api;

import com.dongrame.api.domain.user.dto.UserResponseDto;
import com.dongrame.api.domain.user.service.UserService;
import com.dongrame.api.global.config.UrlProperties;
import com.dongrame.api.global.util.ApiResponse;
import com.dongrame.api.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "회원")
public class UserController {

    private final UserService userService;
    private final UrlProperties urlProperties;

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 내 정보를 조회합니다.")
    @GetMapping("/my")
    public ApiResponse<UserResponseDto> getUser() {
        UserResponseDto userResponseDto = userService.getCurrentLoginUser();
        return ApiResponse.success(userResponseDto);
    }

    @Operation(summary = "내 이름 수정", description = "이름 수정이 가능합니다.")
    @GetMapping("/update")
    public ApiResponse<Long> updateUsername(
            @RequestParam(name = "username") String username
    ) {
        Long id = userService.updateUsername(username);
        return ApiResponse.success(id);
    }

    @Operation(summary = "회원 탈퇴", description = "탈퇴시 검색 기록 저장 여부를 결정할 수 있습니다.")
    @DeleteMapping("/delete")
    public ApiResponse<Long> deleteUser(
            HttpServletResponse response
    ) {
        Long id = userService.deleteUser();

        CookieUtil.deleteCookie(response, "AccessToken", urlProperties);
        CookieUtil.deleteCookie(response, "RefreshToken", urlProperties);

        return ApiResponse.success(id);
    }
}

