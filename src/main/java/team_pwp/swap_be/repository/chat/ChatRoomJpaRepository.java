package team_pwp.swap_be.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.entity.chat.ChatRoom;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {

}
