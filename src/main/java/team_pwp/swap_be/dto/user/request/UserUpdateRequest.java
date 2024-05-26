package team_pwp.swap_be.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import team_pwp.swap_be.domain.user.UserUpdate;

@Builder
public record UserUpdateRequest(
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
    String nickname
) {

    public UserUpdate toCommand() {
        return UserUpdate.builder()
            .nickname(nickname)
            .build();
    }


}
