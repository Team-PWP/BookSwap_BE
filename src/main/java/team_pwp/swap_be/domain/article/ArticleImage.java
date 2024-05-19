package team_pwp.swap_be.domain.article;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleImage {

    private final Long id;
    private final String title;
    private final String content;
    private final Long buyoutPrice;
    private final Long minPrice;
    private final LocalDateTime createdAt;
    private final LocalDateTime bidStartAt;
    private final LocalDateTime bidEndAt;
    private final List<String> imageUrls;

    // 생성자
    public ArticleImage(Long id, String title, String content, Long buyoutPrice, Long minPrice,
        LocalDateTime createdAt, LocalDateTime bidStartAt, LocalDateTime bidEndAt,
        Object imageUrls) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.buyoutPrice = buyoutPrice;
        this.minPrice = minPrice;
        this.createdAt = createdAt;
        this.bidStartAt = bidStartAt;
        this.bidEndAt = bidEndAt;
        /**
         * imageUrls가 null이면 빈 리스트로 초기화
         */
        if (Objects.isNull(imageUrls)) {
            this.imageUrls = null;
        } else {
            this.imageUrls = Arrays.asList(((String) imageUrls).split(","));
        }
    }
}