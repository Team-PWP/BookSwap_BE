package team_pwp.swap_be.repository.image;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.image.Image;

@Repository
public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    List<Image> findByArticle(Article article);

    /**
     * Article목록 에 해당하는 Image들을 조회한다.
     */
    List<Image> findByArticleIn(List<Article> articles);
}
