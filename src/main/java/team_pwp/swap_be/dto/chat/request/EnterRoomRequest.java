package team_pwp.swap_be.dto.chat.request;

import jakarta.validation.constraints.NotNull;

public record EnterRoomRequest(
    @NotNull
    Long chatRoomId,
    @NotNull
    Long userId
) {

}
