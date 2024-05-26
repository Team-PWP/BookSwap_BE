package team_pwp.swap_be.domain.article;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleCreate {

    private final String title;
    private final String content;
    private final Long buyoutPrice;
    private final Long minPrice;
    private final LocalDateTime createdAt;
    private final LocalDateTime bidStartAt;
    private final LocalDateTime bidEndAt;


}
