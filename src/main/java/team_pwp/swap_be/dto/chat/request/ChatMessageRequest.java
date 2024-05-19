package team_pwp.swap_be.dto.chat.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ChatMessageRequest(
    @NotNull
    Long chatRoomId,
    @NotNull
    Long userId,
    @NotEmpty
    String message
) {

}
