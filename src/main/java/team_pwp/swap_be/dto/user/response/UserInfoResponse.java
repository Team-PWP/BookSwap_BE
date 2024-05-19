package team_pwp.swap_be.dto.user.response;

import lombok.Builder;
import team_pwp.swap_be.entity.user.User;

@Builder
public record UserInfoResponse(
    Long userId,
    String nickname,
    String email,
    String userName
) {

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .userName(user.getUsername())
            .build();
    }
}
