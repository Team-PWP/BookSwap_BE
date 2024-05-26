package team_pwp.swap_be.entity.image;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_pwp.swap_be.entity.article.Article;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @NotNull
    private String imageUrl;

    @Builder
    private Image(Article article, String imageUrl) {
        this.article = article;
        this.imageUrl = imageUrl;
    }

    public static Image createImage(Article article, String imageUrl) {
        return Image.builder()
            .article(article)
            .imageUrl(imageUrl)
            .build();
    }
}
