package team_pwp.swap_be.service.bid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_pwp.swap_be.domain.Bid.BidCreate;
import team_pwp.swap_be.dto.bid.request.BidRequest;
import team_pwp.swap_be.dto.bid.response.BidResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.bid.Bid;
import team_pwp.swap_be.entity.user.User;
import team_pwp.swap_be.repository.article.ArticleJpaRepository;
import team_pwp.swap_be.repository.bid.BidJpaRepository;
import team_pwp.swap_be.repository.user.UserJpaRepository;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BidService {

    private final BidJpaRepository bidJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;


    /**
     * 입찰하기
     *
     * @param bidCreate
     * @param userId
     */
    public BidResponse createBid(BidCreate bidCreate, long userId) {
        log.info("입찰하기");
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저 정보 조회 실패"));
        Article article = articleJpaRepository.findById(bidCreate.getArticleId())
            .orElseThrow(() -> new IllegalArgumentException("게시글 정보 조회 실패"));

        /**
         * 입찰가격이 즉시구매가격보다 높은 경우
         */
        if (bidCreate.getPrice() > article.getBuyoutPrice()) {
            throw new IllegalArgumentException("즉시 구매가격보다 높은 가격으로 입찰할 수 없습니다.");
        }

        /**
         * 입찰가격이 현재 최고가격보다 낮은 경우
         */
        Long currentPrice = bidJpaRepository.findHighestBidPriceByArticleId(
            bidCreate.getArticleId());
        if (currentPrice != null && bidCreate.getPrice() <= currentPrice) {
            throw new IllegalArgumentException("현재 최고가격 이하의 가격으로 입찰할 수 없습니다.");
        }

        /**
         * 입찰 기간 확인
         */
        if (article.getBidStartAt().isAfter(bidCreate.getBidTime())) {
            throw new IllegalArgumentException("입찰 기간이 시작되지 않은 게시글입니다.");
        }
        if (article.getBidEndAt().isBefore(bidCreate.getBidTime())) {
            throw new IllegalArgumentException("입찰 기간이 종료된 게시글입니다.");
        }

        /**
         * 이전 입찰 기록 제거
         */
        bidJpaRepository.deleteByArticleIdAndUserId(bidCreate.getArticleId(), userId);

        Bid bid = Bid.from(bidCreate, article, user);
        bidJpaRepository.save(bid);

        return BidResponse.from(bid, user.getNickname());
    }


    /**
     * 입찰 취소 하기
     */
    public void cancelBid(Long bidId, Long userId) {
        log.info("입찰 취소 하기");

        Bid bid = bidJpaRepository.findById(bidId)
            .orElseThrow(() -> new IllegalArgumentException("입찰 정보 조회 실패"));

        if (!bid.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("입찰자만 입찰을 취소할 수 있습니다.");
        }

        bidJpaRepository.deleteById(bidId);
    }

    /**
     * 게시글 입찰 내역 조회
     */
    public PagingResponse<BidResponse> getBidsByArticleId(PagingRequest pagingRequest,
        Long articleId) {
        Page<Bid> bids = bidJpaRepository.findAllByArticleId(articleId, pagingRequest.toPageable());
        return PagingResponse.from(bids, bid -> BidResponse.from(bid, bid.getUser().getNickname()));
    }

    /**
     * 유저별 입찰 목록 조회
     */
    public PagingResponse<BidResponse> getBidsByUser(PagingRequest pagingRequest, long userId) {
        Page<Bid> bids = bidJpaRepository.findAllByUserId(userId, pagingRequest.toPageable());
        return PagingResponse.from(bids, bid -> BidResponse.from(bid, bid.getArticle().getTitle()));
    }
}
