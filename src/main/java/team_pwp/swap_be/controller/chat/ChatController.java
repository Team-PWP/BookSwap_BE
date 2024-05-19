package team_pwp.swap_be.controller.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.chat.request.ChatMessageRequest;
import team_pwp.swap_be.dto.chat.request.EnterRoomRequest;
import team_pwp.swap_be.entity.chat.ChatMessage;
import team_pwp.swap_be.entity.chat.ChatRoom;
import team_pwp.swap_be.entity.user.User;
import team_pwp.swap_be.service.chat.ChatService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 채팅방에 메시지를 전송하는 컨트롤러
     *
     * @param chatMessageRequest
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Valid ChatMessageRequest chatMessageRequest) {
        /**
         * 채팅방에 메시지를 전송하는 로직
         */
        chatService.sendMessage(chatMessageRequest);
    }

    /**
     * 채팅방에 사용자를 추가하는 컨트롤러
     *
     * @param enterRoomRequest
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Valid EnterRoomRequest enterRoomRequest) {
        /**
         * 채팅방에 사용자를 추가하는 로직
         */
        chatService.EnterRoom(enterRoomRequest);
    }


}
