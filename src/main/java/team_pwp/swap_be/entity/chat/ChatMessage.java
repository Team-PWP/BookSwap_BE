package team_pwp.swap_be.entity.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_pwp.swap_be.entity.user.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    @NotNull
    private String message;

    @Builder
    private ChatMessage(ChatRoom chatRoom, User user, LocalDateTime createdAt, String message) {
        this.chatRoom = chatRoom;
        this.user = user;
        this.createdAt = createdAt;
        this.message = message;
    }

    public static ChatMessage create(ChatRoom chatRoom, User user, String message) {
        return ChatMessage.builder()
            .chatRoom(chatRoom)
            .user(user)
            .createdAt(LocalDateTime.now())
            .message(message)
            .build();
    }

}
