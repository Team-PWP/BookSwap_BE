package team_pwp.swap_be.controller.article;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team_pwp.swap_be.dto.article.request.ArticleCreateRequest;
import team_pwp.swap_be.dto.article.response.ArticleInfoResponse;
import team_pwp.swap_be.dto.article.response.ArticleResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.service.article.ArticleService;

@Tag(name = "게시글", description = "게시글 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Operation(summary = "게시글 생성", description = "게시글 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createArticle(
        @Valid @RequestPart("article") ArticleCreateRequest articleCreateRequest,
        @RequestPart("images") List<MultipartFile> images,
        Principal principal) {
        log.info("게시글 생성");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(articleService.createArticle(articleCreateRequest.toCommand(),
                images, Long.parseLong(principal.getName())));
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정")
    @PutMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> updateArticle(@PathVariable Long articleId,
        @Valid @RequestPart("article") ArticleCreateRequest articleUpdateRequest,
        @RequestPart("images") List<MultipartFile> images, Principal principal) {
        log.info("게시글 수정");
        return ResponseEntity.status(HttpStatus.OK)
            .body(articleService.updateArticle(articleId, articleUpdateRequest.toCommand(),
                images, Long.parseLong(principal.getName())));
    }


    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @DeleteMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteArticle(@PathVariable Long articleId, Principal principal) {
        log.info("게시글 삭제");
        articleService.deleteArticle(articleId, Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 성공");
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회")
    @GetMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ArticleInfoResponse> getArticle(@PathVariable Long articleId) {
        log.info("게시글 상세 조회");
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleInfo(articleId));
    }

    @Operation(summary = "게시글 페이징 조회", description = "쿼리 파라미터 확인 부탁드립니다! ex) page=0&size=10")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<ArticleResponse> getArticles(@Valid PagingRequest pagingRequest) {
        log.info("게시글 페이징 조회");
        return articleService.getArticlePaging(pagingRequest);
    }

    @Operation(summary = "유저 작성 게시글 페이징 조회", description = "쿼리 파라미터 확인 부탁드립니다! ex) user/{유저아이디}?page=0&size=10")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<ArticleResponse> getUserArticles(@Valid PagingRequest pagingRequest,
        @PathVariable Long userId) {
        log.info("유저 작성 게시글 페이징 조회");
        return articleService.getUserArticlePaging(pagingRequest,
            userId);
    }

    @Operation(summary = "게시글 이름 검색 페이징", description = "쿼리 파라미터 확인 부탁드립니다! ex) page=0&size=10&keyword=검색어")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<ArticleResponse> searchArticles(@Valid PagingRequest pagingRequest,
        @RequestParam String keyword) {
        log.info("게시글 이름 검색 페이징 조회");
        return articleService.searchArticlePaging(keyword, pagingRequest);
    }

    @Operation(summary = "업로드 테스트", description = "업로드 테스트")
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> uploadTest(@RequestParam("file") MultipartFile file) {
        log.info("업로드 테스트");
        try {
            String fileName = file.getOriginalFilename();
            String fileUrl = "https://" + bucket + "/test" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
