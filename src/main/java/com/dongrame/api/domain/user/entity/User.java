package com.dongrame.api.domain.user.entity;

import com.dongrame.api.domain.user.dto.UserUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kakaoId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImage;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    private boolean active = true;

    public void updateUserType(UserType userType) {
        this.userType = userType;
    }

    public void update(UserUpdateRequestDto dto) {
        this.nickname = dto.getUsername();
        this.email = dto.getEmail();
        this.userType = dto.getUserType();
    }
}
