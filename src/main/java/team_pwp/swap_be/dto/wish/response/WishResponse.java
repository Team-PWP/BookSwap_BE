package team_pwp.swap_be.dto.wish.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import team_pwp.swap_be.domain.article.ArticleImage;
import team_pwp.swap_be.domain.wish.WishArticleImage;
import team_pwp.swap_be.dto.article.response.ArticleResponse;

@Builder
public record WishResponse(
    Long wishId,
    Long articleId,
    String userNickname,
    String title,
    Long buyoutPrice,
    Long minPrice,
    LocalDateTime createdAt,
    LocalDateTime bidStartAt,
    LocalDateTime bidEndAt,
    List<String> imageUrl
) {

    public static WishResponse from(WishArticleImage wishArticleImage) {
        return WishResponse.builder()
            .wishId(wishArticleImage.getId())
            .articleId(wishArticleImage.getArticleId())
            .userNickname(wishArticleImage.getUserNickname())
            .title(wishArticleImage.getTitle())
            .buyoutPrice(wishArticleImage.getBuyoutPrice())
            .minPrice(wishArticleImage.getMinPrice())
            .createdAt(wishArticleImage.getCreatedAt())
            .bidStartAt(wishArticleImage.getBidStartAt())
            .bidEndAt(wishArticleImage.getBidEndAt())
            .imageUrl(wishArticleImage.getImageUrls())
            .build();

    }

}
