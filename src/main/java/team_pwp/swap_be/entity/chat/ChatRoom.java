package team_pwp.swap_be.entity.chat;

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
import team_pwp.swap_be.entity.article.Article;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private String title;

    @Builder
    private ChatRoom(Article article, LocalDateTime createdAt, String title) {
        this.article = article;
        this.createdAt = createdAt;
        this.title = title;
    }

    public static ChatRoom create(Article article, String title) {
        return ChatRoom.builder()
            .article(article)
            .createdAt(LocalDateTime.now())
            .title(title)
            .build();
    }
}
