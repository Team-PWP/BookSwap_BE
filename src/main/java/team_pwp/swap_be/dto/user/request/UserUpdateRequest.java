package team_pwp.swap_be.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import team_pwp.swap_be.domain.user.UserUpdate;

@Builder
public record UserUpdateRequest(
    @NotBlank(message = "닉네임은 필수입니다.")
    String nickName
) {

    public UserUpdate toCommand() {
        return UserUpdate.builder()
            .nickName(nickName)
            .build();
    }


}
