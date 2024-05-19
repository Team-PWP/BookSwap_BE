package team_pwp.swap_be.dto.chat.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


public record ChatRoomCreateRequest(
    @NotNull
    Long articleId,
    @NotNull
    Long buyerId,
    @NotNull
    Long sellerId,
    @NotBlank
    String title
) {

}
