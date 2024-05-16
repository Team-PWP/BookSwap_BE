package team_pwp.swap_be.repository.article;

import org.springframework.data.jpa.repository.JpaRepository;
import team_pwp.swap_be.entity.article.Article;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {

}
