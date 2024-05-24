package team_pwp.swap_be.service.wish;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_pwp.swap_be.domain.article.ArticleImage;
import team_pwp.swap_be.dto.article.response.ArticleInfoResponse;
import team_pwp.swap_be.dto.article.response.ArticleResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.user.User;
import team_pwp.swap_be.entity.wish.Wish;
import team_pwp.swap_be.repository.article.ArticleJpaRepository;
import team_pwp.swap_be.repository.user.UserJpaRepository;
import team_pwp.swap_be.repository.wish.WishJpaRepository;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class WishService {

    private final ArticleJpaRepository articleJpaRepository;
    private final WishJpaRepository wishJpaRepository;
    private final UserJpaRepository userJpaRepository;

    /**
     * 찜하기
     */
    public Long createWish(Long articleId, Long userId) {
        log.info("찜 하기");

        Article article = articleJpaRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        // 찜한 게시글이 이미 찜한 게시글인지 확인
        if (wishJpaRepository.existsByArticleAndUser(article, user)) {
            throw new IllegalArgumentException("이미 찜한 게시글입니다.");
        }
        // 찜하기
        Wish wish = Wish.createWish(user, article);
        wishJpaRepository.save(wish);

        return wish.getId();
    }

    public PagingResponse<ArticleResponse> getWishList(PagingRequest pagingRequest, Long userId) {
        log.info("찜 목록 조회");

        Page<ArticleImage> articleImages = wishJpaRepository.findAllByUserId(userId,
            pagingRequest.toPageable());
        return PagingResponse.from(articleImages, ArticleResponse::from);
    }
}
