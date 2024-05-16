package team_pwp.swap_be.dto.article.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record ArticleUpdateRequest(
    @NotBlank
    String title,
    @NotBlank
    String content,
    @NotBlank
    Long buyoutPrice,
    @NotBlank
    Long minPrice,
    @NotBlank
    LocalDateTime bidStartAt,
    @NotBlank
    LocalDateTime bidEndAt,
    List<String> imageUrls
) {

}
