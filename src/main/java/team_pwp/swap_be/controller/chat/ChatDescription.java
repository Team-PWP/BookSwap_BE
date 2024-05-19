package team_pwp.swap_be.controller.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "채팅 설명서", description = "채팅 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/chat")
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
}
