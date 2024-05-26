package team_pwp.swap_be.controller.bid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.bid.request.BidRequest;
import team_pwp.swap_be.dto.bid.response.BidResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.service.bid.BidService;

@Tag(name = "입찰", description = "입찰 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/bid")
public class BidController {

    private final BidService bidService;

    @Operation(summary = "입찰 하기", description = "입찰 하기")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BidResponse> createBid(@Valid @RequestBody BidRequest bidRequest,
        Principal principal) {
        log.info("입찰 하기");
        BidResponse bidResponse = bidService.createBid(bidRequest.toCommand(),
            Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(bidResponse);
    }

    @Operation(summary = "입찰 취소 하기", description = "입찰 취소 하기")
    @DeleteMapping("/{bidId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createBuyout(@PathVariable Long bidId, Principal principal) {
        log.info("입찰 취소 하기");
        bidService.cancelBid(bidId, Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.OK).body("입찰 취소 성공");
    }

    @Operation(summary = "게시글 입찰 내역 페이징 조회", description = "게시글 입찰 내역 조회")
    @GetMapping("/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<BidResponse> getBidsByArticleId(@Valid PagingRequest pagingRequest,
        @PathVariable Long articleId) {
        log.info("게시글 입찰 내역 조회");
        return bidService.getBidsByArticleId(pagingRequest, articleId);
    }

    @Operation(summary = "유저별 입찰 목록 페이징조회", description = "유저별 입찰 목록 조회")
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<BidResponse> getBidsByUserId(@Valid PagingRequest pagingRequest,
        Principal principal) {
        log.info("유저별 입찰 목록 조회");
        return bidService.getBidsByUser(pagingRequest, Long.parseLong(principal.getName()));
    }

}
