package team_pwp.swap_be.service.article;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_pwp.swap_be.domain.article.ArticleCreate;
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
    private final ImageJpaRepository ImageJpaRepository;

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
            .forEach(ImageJpaRepository::save);

        return article.getId();
    }

}
