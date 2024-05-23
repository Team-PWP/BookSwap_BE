package team_pwp.swap_be.entity.bid;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import team_pwp.swap_be.domain.Bid.BidCreate;
import team_pwp.swap_be.dto.bid.request.BidRequest;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.user.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Long price;

    @NotNull
    private LocalDateTime bidTime;

    @Builder
    private Bid(Article article, User user, Long price, LocalDateTime bidTime) {
        this.article = article;
        this.user = user;
        this.price = price;
        this.bidTime = bidTime;
    }

    public static Bid from(BidCreate bidCreate, Article article, User user) {
        return Bid.builder()
            .article(article)
            .user(user)
            .price(bidCreate.getPrice())
            .bidTime(bidCreate.getBidTime())
            .build();
    }
}
