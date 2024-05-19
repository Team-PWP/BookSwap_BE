package team_pwp.swap_be.dto.chat.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


public record ChatRoomCreateRequest(
    @NotBlank
    Long articleId,
    @NotBlank
    Long buyerId,
    @NotBlank
    Long sellerId,
    @NotBlank
    String title
) {

}
