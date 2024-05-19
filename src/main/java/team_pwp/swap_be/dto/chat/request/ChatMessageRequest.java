package team_pwp.swap_be.dto.chat.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ChatMessageRequest(
    @NotBlank
    Long chatRoomId,
    @NotBlank
    Long userId,
    @NotEmpty
    String message
) {

}
