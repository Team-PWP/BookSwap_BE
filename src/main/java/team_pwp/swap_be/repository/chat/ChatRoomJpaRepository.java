package team_pwp.swap_be.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import team_pwp.swap_be.entity.chat.ChatRoom;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {

}
