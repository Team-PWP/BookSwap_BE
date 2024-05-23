package team_pwp.swap_be.dto.article.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import team_pwp.swap_be.domain.article.ArticleCreate;

public record ArticleCreateRequest(
    @NotBlank(message = "제목은 필수입니다.")
    String title,
    @NotBlank(message = "내용은 필수입니다.")
    String content,
    @NotNull(message = "즉시 구매가격은 필수입니다.")
    Long buyoutPrice,
    @NotNull(message = "최소 입찰가격은 필수입니다.")
    Long minPrice,
    @NotNull(message = "입찰 시작일은 필수입니다.")
    LocalDateTime bidStartAt,
    @NotNull(message = "입찰 종료일은 필수입니다.")
    LocalDateTime bidEndAt
) {

    /**
     * bidStartAt은 현재 시각 보다 이후여야 합니다.
     *
     * @return
     */
    private boolean isBidStartAtValid() {
        return bidStartAt.isAfter(LocalDateTime.now());
    }

    /**
     * bidEndAt은 bidStartAt 보다 최소 1시간이후여야 합니다.
     *
     * @return
     */
    private boolean isBidEndAtValid() {
        return bidEndAt.isAfter(bidStartAt.plusHours(1));
    }

    /**
     * minPrice는 0이상 입니다.
     */
    private boolean isMinPriceValid() {
        return minPrice >= 0;
    }

    /**
     * buyoutPrice는 minPrice보다 커야합니다.
     */
    private boolean isBuyoutPriceValid() {
        return buyoutPrice > minPrice;
    }

    /**
     * ArticleCreateRequest를 ArticleCreate로 변환합니다. 위 두 메서드의 결과가 true일 때만 변환합니다.
     *
     * @return
     */
    public ArticleCreate toCommand() {
        if (!isBidStartAtValid() || !isBidEndAtValid()) {
            throw new IllegalArgumentException(
                "입찰 시작일과 종료일을 확인해주세요. 입찰 시작일은 현재시각 이후여야하며, 입찰 종료일은 입찰 시작일 이후 최소 1시간 이후여야 합니다.");
        }
        if (!isMinPriceValid()) {
            throw new IllegalArgumentException("최소 입찰가격은 0 이상이어야 합니다.");
        }
        if (!isBuyoutPriceValid()) {
            throw new IllegalArgumentException("즉시 구매가격은 최소 입찰가격보다 커야합니다.");
        }

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
