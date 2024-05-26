package team_pwp.swap_be.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.dto.chat.response.ChatMessageResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.entity.chat.ChatMessage;

@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 생성날짜 별로 채팅 메세지 페이징 조회
     *
     * @param chatRoomId
     * @param pageable
     * @return
     */
    Page<ChatMessage> findChatMessageByChatRoomIdOrderByCreatedAt(Long chatRoomId,
        Pageable pageable);

}
