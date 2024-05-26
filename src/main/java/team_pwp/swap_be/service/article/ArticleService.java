package team_pwp.swap_be.service.article;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
import team_pwp.swap_be.repository.bid.BidJpaRepository;
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
    private final AmazonS3 amazonS3;
    private final S3Service s3Service;
    private final BidJpaRepository bidJpaRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 게시글 생성 및 imageUrl 저장
     */
    public Long createArticle(ArticleCreate articleCreateRequest, List<MultipartFile> imageFiles,
        Long userId) {
        /**
         * 게시글 생성
         * imageUrl 저장
         */
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저 정보 조회 실패"));
        Article article = Article.createArticle(articleCreateRequest, user);
        articleJpaRepository.save(article);

        log.info("이미지 파일 개수 size:{}", imageFiles.size());
        /**
         * 이미지 파일이 있는 경우 업로드하고 URL을 리스트로 생성
         */
        List<String> imageUrls = new ArrayList<>();

        log.info("이미지 파일 개수 size:{}", imageFiles.size());
        log.info("이미지가 있는지 확인 {}", imageFiles.isEmpty());
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile file : imageFiles) {
                if (file.isEmpty()) {
                    break;
                }
                log.info("이미지 파일 이름:{}", file.getOriginalFilename());
                try {
                    String fileUrl = s3Service.uploadFile(file);
                    imageUrls.add(fileUrl);
                } catch (IOException e) {
                    log.error("파일 업로드 중 오류 발생", e);
                    throw new IllegalArgumentException("파일 업로드 중 오류 발생");
                }
            }

            /**
             * URL을 기반으로 이미지 엔티티를 생성하고 저장
             */
            imageUrls.stream()
                .map(imageUrl -> Image.createImage(article, imageUrl))
                .forEach(imageJpaRepository::save);
        }

        return article.getId();
    }


    /**
     * 게시글 상세 조회
     *
     * @param articleId
     * @return ArticleResponse
     */
    @Transactional(readOnly = true)
    public ArticleInfoResponse getArticleInfo(Long articleId) {
        Article article = articleJpaRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 게시글이 없습니다."));
        List<Image> images = imageJpaRepository.findByArticle(article);

        Long currentPrice = bidJpaRepository.findHighestBidPriceByArticleId(articleId);
        return ArticleInfoResponse.from(article, images, currentPrice);
    }

    /**
     * 게시글 페이징 조회
     *
     * @param pagingRequest
     * @return PagingResponse<ArticleResponse>
     */
    @Transactional(readOnly = true)
    public PagingResponse<ArticleResponse> getArticlePaging(PagingRequest pagingRequest) {
        Page<ArticleImage> articleImages = articleJpaRepository.findAllArticleImage(
            pagingRequest.toPageable());
        return PagingResponse.from(articleImages, ArticleResponse::from);
    }

    /**
     * 게시글 수정
     *
     * @param articleId
     * @param articleUpdate
     */
    public Long updateArticle(Long articleId, ArticleCreate articleUpdate,
        List<MultipartFile> imageFiles, Long userId) {
        Article article = articleJpaRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 게시글이 없습니다."));
        User user = userJpaRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("id에 해당하는 유저가 없습니다."));
        if (!article.getUser().equals(user)) {
            throw new IllegalArgumentException("게시글 작성자만 수정 가능합니다.");
        }

        /**
         * 기존 이미지 삭제
         */
        List<Image> images = imageJpaRepository.findByArticle(article);
        images.forEach(imageJpaRepository::delete);

        /**
         * 새 이미지 파일을 업로드하고 URL을 리스트로 생성
         */
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if (file.isEmpty()) {
                break;
            }
            try {
                String fileUrl = s3Service.uploadFile(file);
                imageUrls.add(fileUrl);
            } catch (IOException e) {
                log.error("파일 업로드 중 오류 발생", e);
                throw new IllegalArgumentException("파일 업로드 중 오류 발생");
            }
        }

        /**
         * 새 URL을 기반으로 이미지 엔티티를 생성하고 저장
         */
        imageUrls.stream()
            .map(imageUrl -> Image.createImage(article, imageUrl))
            .forEach(imageJpaRepository::save);

        article.updateArticle(articleUpdate);
        return article.getId();
    }


    /**
     * 게시글 삭제
     *
     * @param articleId
     */
    public void deleteArticle(Long articleId, Long userId) {
        Article article = articleJpaRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 게시글이 없습니다."));

        if (!article.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("게시글 작성자만 삭제 가능합니다.");
        }

        List<Image> images = imageJpaRepository.findByArticle(article);
        images.forEach(imageJpaRepository::delete);
        articleJpaRepository.delete(article);
    }

    /**
     * 유저 작성 게시글 페이징 조회
     *
     * @param pagingRequest
     * @param userId
     * @return PagingResponse<ArticleResponse>
     */
    @Transactional(readOnly = true)
    public PagingResponse<ArticleResponse> getUserArticlePaging(PagingRequest pagingRequest,
        Long userId) {
        Page<ArticleImage> articleImages = articleJpaRepository.findAllByUserId(userId,
            pagingRequest.toPageable());
        return PagingResponse.from(articleImages, ArticleResponse::from);
    }

    /**
     * 게시글 검색
     *
     * @param keyword
     * @param pagingRequest
     */
    public PagingResponse<ArticleResponse> searchArticlePaging(String keyword,
        PagingRequest pagingRequest) {
        log.info(keyword);
        Page<ArticleImage> articleImages = articleJpaRepository.findByTitleContain(keyword,
            pagingRequest.toPageable());
        return PagingResponse.from(articleImages, ArticleResponse::from);
    }
}

