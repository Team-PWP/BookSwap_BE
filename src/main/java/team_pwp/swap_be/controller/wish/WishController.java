package team_pwp.swap_be.controller.wish;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.article.response.ArticleResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.entity.wish.Wish;
import team_pwp.swap_be.service.wish.WishService;

@Tag(name = "찜", description = "찜 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wish")
public class WishController {

    private final WishService wishService;

    @Operation(summary = "찜 하기", description = "찜 하기")
    @PostMapping("/{articleId}")
    public ResponseEntity<Long> createWish(@PathVariable Long articleId, Principal principal) {
        log.info("찜 하기");
        return ResponseEntity.ok(
            wishService.createWish(articleId, Long.parseLong(principal.getName())));
    }

    @Operation(summary = "찜 목록 조회", description = "찜 목록 조회")
    @GetMapping
    public PagingResponse<ArticleResponse> getWishList(@Valid PagingRequest pagingRequest,
        Principal principal) {
        log.info("찜 목록 조회");
        return wishService.getWishList(pagingRequest, Long.parseLong(principal.getName()));
    }

    @Operation(summary = "찜 취소 하기", description = "찜 취소 하기")
    @DeleteMapping("/{articleId}")
    public void deleteWish(@PathVariable Long articleId, Principal principal) {
        log.info("찜 취소 하기");
    }

}
