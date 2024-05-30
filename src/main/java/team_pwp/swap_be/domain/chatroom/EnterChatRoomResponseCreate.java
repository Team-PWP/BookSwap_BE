package team_pwp.swap_be.domain.chatroom;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterChatRoomResponseCreate {

    Long chatRoomId;
    Long articleId;
    String title;
}
