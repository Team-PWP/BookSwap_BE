package team_pwp.swap_be.dto.bid.response;

import java.time.LocalDateTime;
import lombok.Builder;
import team_pwp.swap_be.entity.bid.Bid;

@Builder
public record BidResponse(
    Long bidId,
    Long articleId,
    String userNickname,
    Long price,
    LocalDateTime bidTime
) {

    public static BidResponse from(Bid bid, String userNickname) {
        return BidResponse.builder()
            .bidId(bid.getId())
            .articleId(bid.getArticle().getId())
            .userNickname(userNickname)
            .price(bid.getPrice())
            .bidTime(bid.getBidTime())
            .build();
    }
}
