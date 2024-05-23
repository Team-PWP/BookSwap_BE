package team_pwp.swap_be.domain.Bid;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BidCreate {

    private final Long articleId;
    private final Long price;
    private final LocalDateTime bidTime;


}
