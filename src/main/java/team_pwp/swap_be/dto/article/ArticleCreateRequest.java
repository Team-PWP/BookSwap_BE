package team_pwp.swap_be.dto.article;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import team_pwp.swap_be.domain.article.ArticleCreate;

public record ArticleCreateRequest(
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

    public ArticleCreate toCommand() {
        return ArticleCreate.builder()
            .title(title)
            .content(content)
            .buyoutPrice(buyoutPrice)
            .minPrice(minPrice)
            .bidStartAt(bidStartAt)
            .bidEndAt(bidEndAt)
            .createdAt(LocalDateTime.now())
            .build();
    }

}
