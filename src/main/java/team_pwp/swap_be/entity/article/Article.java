package team_pwp.swap_be.entity.article;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import jdk.jfr.Name;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_pwp.swap_be.domain.article.ArticleCreate;
import team_pwp.swap_be.entity.user.User;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Long buyoutPrice;

    @NotNull
    private Long minPrice;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime bidStartAt;

    @NotNull
    private LocalDateTime bidEndAt;

    @Builder
    private Article(User user, String title, String content, Long buyoutPrice, Long minPrice,
        LocalDateTime createdAt, LocalDateTime bidStartAt, LocalDateTime bidEndAt) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.buyoutPrice = buyoutPrice;
        this.minPrice = minPrice;
        this.createdAt = createdAt;
        this.bidStartAt = bidStartAt;
        this.bidEndAt = bidEndAt;
    }

    public static Article createArticle(ArticleCreate articleCreate, User user) {
        return Article.builder()
            .user(user)
            .title(articleCreate.getTitle())
            .content(articleCreate.getContent())
            .buyoutPrice(articleCreate.getBuyoutPrice())
            .minPrice(articleCreate.getMinPrice())
            .createdAt(articleCreate.getCreatedAt())
            .bidStartAt(articleCreate.getBidStartAt())
            .bidEndAt(articleCreate.getBidEndAt())
            .build();
    }

}
