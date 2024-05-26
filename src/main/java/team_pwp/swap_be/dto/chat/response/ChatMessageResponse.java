package team_pwp.swap_be.dto.chat.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import team_pwp.swap_be.entity.chat.ChatMessage;

@Builder
@Getter
public class ChatMessageResponse {

    String message;
    Long userId;
    String nickname;
    LocalDateTime createdAt;

    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
            .message(chatMessage.getMessage())
            .userId(chatMessage.getUser().getId())
            .createdAt(chatMessage.getCreatedAt())
            .nickname(chatMessage.getUser().getNickname())
            .build();
    }

}
