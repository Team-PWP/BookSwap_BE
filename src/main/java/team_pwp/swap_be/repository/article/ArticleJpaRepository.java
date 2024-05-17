package team_pwp.swap_be.repository.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.domain.article.ArticleImage;
import team_pwp.swap_be.dto.article.response.ArticleResponse;
import team_pwp.swap_be.entity.article.Article;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT new team_pwp.swap_be.domain.article.ArticleImage(" +
        "a.id, a.title, a.content, a.buyoutPrice, a.minPrice, a.createdAt, a.bidStartAt, a.bidEndAt, "
        +
        "GROUP_CONCAT(i.imageUrl)) " +
        "FROM Article a " +
        "LEFT JOIN Image i ON a.id = i.article.id " +
        "GROUP BY a.id " +
        "ORDER BY a.createdAt DESC")
    Page<ArticleImage> findAllArticleImage(Pageable pageable);
}
