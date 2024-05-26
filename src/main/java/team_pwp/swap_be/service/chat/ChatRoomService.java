package team_pwp.swap_be.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_pwp.swap_be.dto.chat.request.ChatRoomCreateRequest;
import team_pwp.swap_be.dto.chat.response.EnterChatRoomResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.chat.ChatRoom;
import team_pwp.swap_be.entity.chat.EnterChatRoom;
import team_pwp.swap_be.entity.user.User;
import team_pwp.swap_be.repository.article.ArticleJpaRepository;
import team_pwp.swap_be.repository.chat.ChatRoomJpaRepository;
import team_pwp.swap_be.repository.chat.EnterChatRoomJpaRepository;
import team_pwp.swap_be.repository.user.UserJpaRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserJpaRepository userJpaRepository;
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final EnterChatRoomJpaRepository enterChatRoomJpaRepository;


    /**
     * 채팅방 생성 1. 구매자, 판매자 생성 2. 채팅방 생성 3. 채팅방에 구매자, 판매자 추가
     *
     * @return
     */
    public Long createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {
        User buyer = userJpaRepository.findById(chatRoomCreateRequest.buyerId()).orElseThrow(() ->
            new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        User seller = userJpaRepository.findById(chatRoomCreateRequest.sellerId()).orElseThrow(() ->
            new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Article article = articleJpaRepository.findById(chatRoomCreateRequest.articleId())
            .orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        // 채팅방 생성
        ChatRoom chatRoom = ChatRoom.create(article, chatRoomCreateRequest.title());

        // 채팅방에 구매자, 판매자 추가
        EnterChatRoom enterChatRoomBuyer = EnterChatRoom.create(chatRoom, buyer);
        EnterChatRoom enterChatRoomSeller = EnterChatRoom.create(chatRoom, seller);

        // 채팅방 저장, 채팅방에 구매자, 판매자 추가
        chatRoomJpaRepository.save(chatRoom);
        enterChatRoomJpaRepository.save(enterChatRoomBuyer);
        enterChatRoomJpaRepository.save(enterChatRoomSeller);

        return chatRoom.getId();
    }

    /**
     * 유저별 채팅방 조회
     *
     * @return
     */
    public PagingResponse<EnterChatRoomResponse> getChatRoom(PagingRequest pagingRequest,
        Long userId) {
        User user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        Page<EnterChatRoom> enterChatRooms = enterChatRoomJpaRepository.findAllByUserId(
            userId, pagingRequest.toPageable());
        return PagingResponse.from(enterChatRooms, EnterChatRoomResponse::from);
    }
}
