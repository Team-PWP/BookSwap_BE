package team_pwp.swap_be.service.article;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_pwp.swap_be.domain.article.ArticleCreate;
import team_pwp.swap_be.domain.article.ArticleImage;
import team_pwp.swap_be.dto.article.response.ArticleInfoResponse;
import team_pwp.swap_be.dto.article.response.ArticleResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.image.Image;
import team_pwp.swap_be.entity.user.User;
import team_pwp.swap_be.repository.article.ArticleJpaRepository;
import team_pwp.swap_be.repository.image.ImageJpaRepository;
import team_pwp.swap_be.repository.user.UserJpaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ArticleService {

    private final UserJpaRepository userJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final ImageJpaRepository imageJpaRepository;

    /**
     * 게시글 생성 및 imageUrl 저장
     *
     * @param articleCreateRequest
     * @param imageUrls
     * @param userId
     * @return 게시글 id
     */
    public Long createArticle(ArticleCreate articleCreateRequest, List<String> imageUrls,
        Long userId) {
        /**
         * 게시글 생성
         * imageUrl 저장
         */
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저 정보 조회 실패"));
        Article article = Article.createArticle(articleCreateRequest, user);
        articleJpaRepository.save(article);
        /**
         * stream을 사용하여 createImage로 imageUrl 저장
         */
        imageUrls.stream()
            .map(imageUrl -> Image.createImage(article, imageUrl))
            .forEach(imageJpaRepository::save);

        return article.getId();
    }

    /**
     * 게시글 상세 조회
     *
     * @param articleId
     * @return ArticleResponse
     */
    public ArticleInfoResponse getArticleInfo(Long articleId) {
        Article article = articleJpaRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 게시글이 없습니다."));
        List<Image> images = imageJpaRepository.findByArticle(article);
        return ArticleInfoResponse.from(article, images);
    }

    /**
     * 게시글 페이징 조회
     *
     * @param pagingRequest
     * @return PagingResponse<ArticleResponse>
     */
    public PagingResponse<ArticleResponse> getArticlePaging(PagingRequest pagingRequest) {
        Page<ArticleImage> articleImages = articleJpaRepository.findAllArticleImage(
            pagingRequest.toPageable());
        return PagingResponse.from(articleImages, ArticleResponse::from);
    }
}

