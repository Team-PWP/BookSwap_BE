package team_pwp.swap_be.controller.wish;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public void createWish(@PathVariable Long articleId, Principal principal) {
        log.info("찜 하기");
    }

    @Operation(summary = "찜 목록 조회", description = "찜 목록 조회")
    @GetMapping
    public void getWishList(Principal principal) {
        log.info("찜 목록 조회");
    }

    @Operation(summary = "찜 취소 하기", description = "찜 취소 하기")
    @DeleteMapping("/{articleId}")
    public void deleteWish(@PathVariable Long articleId, Principal principal) {
        log.info("찜 취소 하기");
    }

}
