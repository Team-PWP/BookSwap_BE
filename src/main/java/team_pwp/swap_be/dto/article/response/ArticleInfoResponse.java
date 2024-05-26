package team_pwp.swap_be.dto.article.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import team_pwp.swap_be.domain.Bid.CurrentPriceUser;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.image.Image;


@Builder
public record ArticleInfoResponse(
    Long userId,
    String title,
    String content,
    Long buyoutPrice,
    Long minPrice,
    LocalDateTime createdAt,
    LocalDateTime bidStartAt,
    LocalDateTime bidEndAt,
    List<String> imageUrls,
    Long currentPrice,
    Long maxBidUserId
) {

    public static ArticleInfoResponse from(Article article, List<Image> images,
        CurrentPriceUser currentPriceUser) {
        return ArticleInfoResponse.builder()
            .userId(article.getUser().getId())
            .title(article.getTitle())
            .content(article.getContent())
            .buyoutPrice(article.getBuyoutPrice())
            .minPrice(article.getMinPrice())
            .createdAt(article.getCreatedAt())
            .bidStartAt(article.getBidStartAt())
            .bidEndAt(article.getBidEndAt())
            .imageUrls(images.stream().map(Image::getImageUrl).toList())
            .currentPrice(currentPriceUser.getCurrentPrice())
            .maxBidUserId(currentPriceUser.getUserId())
            .build();
    }


}
