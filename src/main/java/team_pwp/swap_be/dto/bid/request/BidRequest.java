package team_pwp.swap_be.dto.bid.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import team_pwp.swap_be.domain.Bid.BidCreate;

public record BidRequest(
    @NotNull
    Long articleId,
    @NotNull
    Long price
) {

    public BidCreate toCommand() {
        return BidCreate.builder()
            .articleId(articleId)
            .price(price)
            .bidTime(LocalDateTime.now())
            .build();
    }
}
