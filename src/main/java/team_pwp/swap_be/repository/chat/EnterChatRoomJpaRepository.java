package team_pwp.swap_be.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import team_pwp.swap_be.entity.chat.EnterChatRoom;

public interface EnterChatRoomJpaRepository extends JpaRepository<EnterChatRoom, Long> {

    /**
     * chatRoomId와 userId로 EnterChatRoom을 찾는다.
     */
    boolean existsByChatRoomIdAndUserId(Long chatRoomId, Long userId);
}
