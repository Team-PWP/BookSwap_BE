package team_pwp.swap_be.dto.chat.response;

import lombok.Builder;
import team_pwp.swap_be.domain.chatroom.EnterChatRoomResponseCreate;
import team_pwp.swap_be.entity.article.Article;
import team_pwp.swap_be.entity.chat.ChatRoom;
import team_pwp.swap_be.entity.chat.EnterChatRoom;

@Builder
public record EnterChatRoomResponse(
    Long chatRoomId,
    Long articleId,
    String title
) {

    public static EnterChatRoomResponse from(
        EnterChatRoomResponseCreate enterChatRoomResponseCreate) {
        return EnterChatRoomResponse.builder()
            .chatRoomId(enterChatRoomResponseCreate.getChatRoomId())
            .articleId(enterChatRoomResponseCreate.getArticleId())
            .title(enterChatRoomResponseCreate.getTitle())
            .build();
    }
}
