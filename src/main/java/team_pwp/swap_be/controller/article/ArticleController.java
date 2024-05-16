package team_pwp.swap_be.controller.article;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.domain.article.ArticleCreate;
import team_pwp.swap_be.dto.article.ArticleCreateRequest;
import team_pwp.swap_be.service.article.ArticleService;

@Tag(name = "게시글", description = "게시글 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "게시글 생성", description = "게시글 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createArticle(
        @Valid @RequestBody ArticleCreateRequest articleCreateRequest,
        Principal principal) {
        log.info("게시글 생성");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(articleService.createArticle(articleCreateRequest.toCommand(),
                articleCreateRequest.imageUrls(), Long.parseLong(principal.getName())));
    }

//    @Operation(summary = "게시글 수정", description = "게시글 수정")
//    @PutMapping("/{articleId}")
//    @ResponseStatus(HttpStatus.OK)
//    public void modifyArticle(@PathVariable Long articleId,
//        @Valid @RequestBody ArticleUpdateRequest articleUpdateRequest) {
//        log.info("게시글 수정");
//        articleService.modifyArticle(articleId, articleUpdateRequest.toDomain());
//    }
//
//    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
//    @DeleteMapping("/{articleId}")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteArticle(@PathVariable Long articleId) {
//        log.info("게시글 삭제");
//        articleService.deleteArticle(articleId);
//    }
//
//    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회")
//    @GetMapping("/{articleId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ArticleResponse getArticle(@PathVariable Long articleId) {
//        log.info("게시글 상세 조회");
//        return articleService.getArticle(articleId);
//    }
//
//    @Operation(summary = "게시글 페이징 조회", description = "게시글 페이징 조회")
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public PagingResponse<ArticleResponse> getArticles(@Valid PagingRequest pagingRequest) {
//        log.info("게시글 페이징 조회");
//        return articleService.getArticlePaging(pagingRequest);
//    }
}