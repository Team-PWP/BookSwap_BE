package team_pwp.swap_be.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_pwp.swap_be.dto.chat.response.ChatMessageResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.entity.chat.ChatMessage;
import team_pwp.swap_be.repository.chat.ChatMessageJpaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatMessageService {

    private final ChatMessageJpaRepository chatMessageJpaRepository;

    /**
     * 채팅 메세지 페이징 조회
     *
     * @param chatRoomId
     * @param pagingRequest
     * @return
     */
    public PagingResponse<ChatMessageResponse> getChatMessagePaging(Long chatRoomId,
        PagingRequest pagingRequest) {
        Page<ChatMessage> chatMessages = chatMessageJpaRepository.findChatMessageByChatRoomIdOrderByCreatedAt(
            chatRoomId, pagingRequest.toPageable());
        return PagingResponse.from(chatMessages, ChatMessageResponse::from);
    }
}
