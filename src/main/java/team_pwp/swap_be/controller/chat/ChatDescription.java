package team_pwp.swap_be.controller.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.chat.request.ChatMessageRequest;
import team_pwp.swap_be.dto.chat.response.ChatMessageResponse;
import team_pwp.swap_be.entity.chat.ChatMessage;

@Tag(name = "채팅 설명서", description = "채팅 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app")
public class ChatDescription {

    @Operation(summary = "WebSocket 사용 방법",
        description = "WebSocket을 통해 메시지를 전송하고 받는 방법 \n\n" +
            "WebSocket endpoint: /chat\n" +
            "STOMP 메시징 엔드포인트:\n" +
            " - 메시지 전송: /app/chat.sendMessage\n" +
            " - 사용자 추가: /app/chat.addUser\n" +
            "구독 경로: /sub/chat/room/{roomId}\n")
    @GetMapping("/websocket-info")
    public String websocketInfo() {
        return "WebSocket endpoint: /chat\n" +
            "STOMP 메시징 엔드포인트:\n" +
            " - 메시지 전송: /app/chat.sendMessage\n" +
            " - 사용자 추가: /app/chat.addUser\n" +
            "구독 경로: /sub/chat/room/{roomId}\n";
    }

    @Operation(summary = "채팅방에 메시지 전송",
        description = "http://52.79.101.132:8080/chat\n" +
            "메시지 전송: /app/chat.sendMessage\n" +
            "메시지 수신: /topic/chat/{roomId}\n ")
    @GetMapping("/chat.sendMessage")
    public ChatMessageResponse sendMessage(@Valid ChatMessageRequest chatMessageRequest) {
        /**
         * 채팅방에 메시지를 전송하는 로직
         */
        return ChatMessageResponse.from(ChatMessage.builder()
            .message(chatMessageRequest.message())
            .build());
    }

    @Operation(summary = "채팅방에 사용자 추가",
        description = "전체 채팅방에 사용자를 추가하는 요청 \n\n" +
            "사용자 추가: /app/chat.addUser\n")
    @GetMapping("/chat.addUser")
    public void addUser(@Valid String userId) {
    }
}
