package team_pwp.swap_be.repository.bid;

import java.nio.channels.FileChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.entity.bid.Bid;

@Repository
public interface BidJpaRepository extends JpaRepository<Bid, Long> {

    /**
     * ArticleId로 최고가격 조회
     */

    @Query("SELECT MAX(b.price) FROM Bid b WHERE b.article.id = :articleId")
    Long findHighestBidPriceByArticleId(@Param("articleId") Long articleId);

    void deleteByArticleIdAndUserId(Long articleId, long userId);

    Page<Bid> findAllByArticleId(Long articleId, Pageable pageable);

    Page<Bid> findAllByUserId(long userId, Pageable pageable);

}
