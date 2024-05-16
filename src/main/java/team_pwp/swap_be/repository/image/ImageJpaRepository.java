package team_pwp.swap_be.repository.image;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.image.Image;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    List<Image> findByArticle(Article article);
}
