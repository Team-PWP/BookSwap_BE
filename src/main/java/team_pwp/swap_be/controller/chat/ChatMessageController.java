package team_pwp.swap_be.controller.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.chat.response.ChatMessageResponse;
import team_pwp.swap_be.dto.common.PagingRequest;
import team_pwp.swap_be.dto.common.PagingResponse;
import team_pwp.swap_be.service.chat.ChatMessageService;

@Tag(name = "채팅 메시지", description = "채팅 메시지 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/chatMessage/{chatRoomId}")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Operation(summary = "채팅 메세지 페이징 조회", description = "채팅방으로 채팅메시지를 불러옵니다")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagingResponse<ChatMessageResponse> getChatMessage(@Valid PagingRequest pagingRequest,
        @PathVariable Long chatRoomId) {
        log.info("채팅 메세지 페이징 조회");
        return chatMessageService.getChatMessagePaging(chatRoomId, pagingRequest);
    }


}
