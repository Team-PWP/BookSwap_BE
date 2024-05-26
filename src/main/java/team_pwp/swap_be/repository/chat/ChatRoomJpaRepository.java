package team_pwp.swap_be.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.domain.chatroom.EnterChatRoomResponseCreate;
import team_pwp.swap_be.dto.chat.response.EnterChatRoomResponse;
import team_pwp.swap_be.entity.chat.ChatRoom;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 유저별 채팅방 조회 join을 통해 EnterChatRoom의 chatRoom과 Article의 id와 title을 가져온다.
     */
    @Query(
        "select new team_pwp.swap_be.domain.chatroom.EnterChatRoomResponseCreate(e.chatRoom.id, a.id, a.title) "
            + "from EnterChatRoom e "
            + "join e.chatRoom c "
            + "join c.article a "
            + "where e.user.id = :userId")
    Page<EnterChatRoomResponseCreate> findAllByUserId(Long userId, Pageable pageable);
}
