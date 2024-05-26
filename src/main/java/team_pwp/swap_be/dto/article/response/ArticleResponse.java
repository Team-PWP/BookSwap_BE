package team_pwp.swap_be.dto.article.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import team_pwp.swap_be.domain.article.ArticleImage;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.image.Image;

@Builder
public record ArticleResponse(
    Long articleId,
    Long sellerId,
    String userNickname,
    String title,
    Long buyoutPrice,
    Long minPrice,
    LocalDateTime createdAt,
    LocalDateTime bidStartAt,
    LocalDateTime bidEndAt,
    List<String> imageUrl
) {

    public static ArticleResponse from(ArticleImage articleImage) {
        return ArticleResponse.builder()
            .sellerId(articleImage.getUserId())
            .articleId(articleImage.getId())
            .userNickname(articleImage.getUserNickname())
            .title(articleImage.getTitle())
            .buyoutPrice(articleImage.getBuyoutPrice())
            .minPrice(articleImage.getMinPrice())
            .createdAt(articleImage.getCreatedAt())
            .bidStartAt(articleImage.getBidStartAt())
            .bidEndAt(articleImage.getBidEndAt())
            .imageUrl(articleImage.getImageUrls())
            .build();

    }

}
