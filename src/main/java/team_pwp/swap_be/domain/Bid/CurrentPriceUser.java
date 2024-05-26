package team_pwp.swap_be.domain.Bid;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentPriceUser {

    private final Long currentPrice;
    private final Long userId;

}
