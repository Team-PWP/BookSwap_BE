package team_pwp.swap_be.controller.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.chat.request.ChatRoomCreateRequest;
import team_pwp.swap_be.service.chatroom.ChatRoomService;

@Tag(name = "채팅방", description = "채팅방 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/chatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성", description = "채팅방을 생성합니다")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createChatRoom(
        @Valid @RequestBody ChatRoomCreateRequest chatRoomCreateRequest) {
        log.info("채팅방 생성");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(chatRoomService.createChatRoom(chatRoomCreateRequest));
    }
}
