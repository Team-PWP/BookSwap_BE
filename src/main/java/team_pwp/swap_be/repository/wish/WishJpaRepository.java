package team_pwp.swap_be.repository.wish;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.domain.article.ArticleImage;
import team_pwp.swap_be.domain.wish.WishArticleImage;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.user.User;
import team_pwp.swap_be.entity.wish.Wish;

@Repository
public interface WishJpaRepository extends JpaRepository<Wish, Long> {

    boolean existsByArticleAndUser(Article article, User user);

    @Query(" SELECT new team_pwp.swap_be.domain.wish.WishArticleImage(" +
        "w.id,a.id,a.user, a.title, a.content, a.buyoutPrice, a.minPrice, a.createdAt, a.bidStartAt, a.bidEndAt, "
        +
        "GROUP_CONCAT(i.imageUrl)) " +
        "FROM Wish w " +
        "JOIN w.article a " +
        "LEFT JOIN Image i ON a.id = i.article.id " +
        "WHERE w.user.id = :userId " +
        "GROUP BY a.id " +
        "ORDER BY a.createdAt DESC")
    Page<WishArticleImage> findAllByUserId(Long userId, Pageable pageable);
}
