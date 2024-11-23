package com.dongrame.api.domain.user.entity;

import com.dongrame.api.domain.bookmark.entity.Bookmark;
import com.dongrame.api.domain.review.entity.CommentLike;
import com.dongrame.api.domain.review.entity.Review;
import com.dongrame.api.domain.review.entity.ReviewComment;
import com.dongrame.api.domain.review.entity.ReviewLike;
import com.dongrame.api.domain.user.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private int level;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean isProfileImageUpdated = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewComment> reviewComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    public void updateUserType(UserType userType) {
        this.userType = userType;
    }

    public void update(UserUpdateRequestDto dto) {
        this.nickname = dto.getUsername();
        this.email = dto.getEmail();
        this.userType = dto.getUserType();
    }

    public void updateProfileImage(String newProfileImage) {
        this.profileImage = newProfileImage;
        this.isProfileImageUpdated = true;
    }

    public void updateLevel() {
        int reviewCount = reviews.size();
        int photoReviewCount = (int) reviews.stream().filter(Review::hasPhoto).count();

        if (reviewCount >= 20 || photoReviewCount >= 12) {
            this.level = 5;
        } else if (reviewCount >= 15 || photoReviewCount >= 9) {
            this.level = 4;
        } else if (reviewCount >= 10 || photoReviewCount >= 6) {
            this.level = 3;
        } else if (reviewCount >= 5 || photoReviewCount >= 3) {
            this.level = 2;
        } else {
            this.level = 1;
        }
    }
}
