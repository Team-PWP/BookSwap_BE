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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.bid.request.BidRequest;
import team_pwp.swap_be.dto.bid.response.BidResponse;
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

    @Operation(summary = "즉시 구매 하기", description = "즉시 구매 하기")
    @PostMapping("/buyout")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBuyout() {
        log.info("즉시 구매 하기");
    }

    @Operation(summary = "입찰한 게시글 조회", description = "입찰한 게시글 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getBids() {
        log.info("입찰한 게시글 조회");
    }

}
