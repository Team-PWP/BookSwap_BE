package team_pwp.swap_be.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.entity.chat.ChatRoom;
import team_pwp.swap_be.entity.chat.EnterChatRoom;

@Repository
public interface EnterChatRoomJpaRepository extends JpaRepository<EnterChatRoom, Long> {

    /**
     * chatRoomId와 userId로 EnterChatRoom을 찾는다.
     */
    boolean existsByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    /**
     * 유저 아이디로 채팅방 조회
     *
     * @param userId
     * @return
     */

    Page<EnterChatRoom> findAllByUserId(Long userId, Pageable pageable);
}
