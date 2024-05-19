package team_pwp.swap_be.dto.chat.response;

import lombok.Builder;
import team_pwp.swap_be.entity.chat.ChatRoom;
import team_pwp.swap_be.entity.chat.EnterChatRoom;

@Builder
public record EnterChatRoomResponse(
    Long chatRoomId,
    Long articleId,
    String title
) {

    public static EnterChatRoomResponse from(EnterChatRoom enterChatRoom) {
        return EnterChatRoomResponse.builder()
            .chatRoomId(enterChatRoom.getChatRoom().getId())
            .articleId(enterChatRoom.getChatRoom().getArticle().getId())
            .title(enterChatRoom.getChatRoom().getTitle())
            .build();
    }
}
