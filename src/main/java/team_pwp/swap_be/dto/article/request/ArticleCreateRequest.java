package team_pwp.swap_be.dto.article.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import team_pwp.swap_be.domain.article.ArticleCreate;

public record ArticleCreateRequest(
    @NotBlank
    String title,
    @NotBlank
    String content,
    @NotBlank
    Long buyoutPrice,
    @NotBlank
    Long minPrice,
    @NotBlank
    LocalDateTime bidStartAt,
    @NotBlank
    LocalDateTime bidEndAt,
    List<String> imageUrls
) {

    /**
     * bidStartAt은 현재 시각 보다 이후여야 합니다.
     *
     * @return
     */
    public boolean isBidStartAtValid() {
        return bidStartAt.isAfter(LocalDateTime.now());
    }

    /**
     * bidEndAt은 bidStartAt 보다 최소 1시간이후여야 합니다.
     *
     * @return
     */
    public boolean isBidEndAtValid() {
        return bidEndAt.isAfter(bidStartAt.plusHours(1));
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
