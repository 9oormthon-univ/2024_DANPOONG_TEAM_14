package com.dongrame.api.domain.user.api;

import com.dongrame.api.domain.user.dto.UserRequestDto;
import com.dongrame.api.domain.user.dto.UserResponseDto;
import com.dongrame.api.domain.user.dto.UserUpdateRequestDto;
import com.dongrame.api.domain.user.service.UserService;
import com.dongrame.api.global.config.UrlProperties;
import com.dongrame.api.global.util.ApiResponse;
import com.dongrame.api.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "회원")
public class UserController {

    private final UserService userService;
    private final UrlProperties urlProperties;

    @Operation(summary = "회원 유형 설정", description = "회원 유형을 설정합니다.<br>" +
            "- DISABLED : 장애인<br>" +
            "- ASSISTANCE_DOG : 보조견<br>" +
            "- ELDERLY : 노약자<br>" +
            "- CHILD : 어린이"
    )
    @PostMapping("/save")
    public ApiResponse<Long> save(
            @RequestBody UserRequestDto userRequestDto
    ) {
        Long id = userService.updateUserType(userRequestDto.getUserType());
        return ApiResponse.success(id);
    }

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 내 정보를 조회합니다.")
    @GetMapping("/my")
    public ApiResponse<UserResponseDto> getUser() {
        UserResponseDto userResponseDto = userService.getCurrentLoginUser();
        return ApiResponse.success(userResponseDto);
    }

    @Operation(summary = "내 정보 수정", description = "정보 수정이 가능합니다.")
    @PatchMapping("/update")
    public ApiResponse<Long> updateUser(
            @RequestBody UserUpdateRequestDto dto
    ) {
        Long id = userService.updateUser(dto);
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

