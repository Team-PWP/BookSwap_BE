package team_pwp.swap_be.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_pwp.swap_be.dto.chat.request.ChatMessageRequest;
import team_pwp.swap_be.dto.chat.request.EnterRoomRequest;
import team_pwp.swap_be.dto.chat.response.ChatMessageResponse;
import team_pwp.swap_be.entity.chat.ChatMessage;
import team_pwp.swap_be.entity.chat.ChatRoom;
import team_pwp.swap_be.entity.chat.EnterChatRoom;
import team_pwp.swap_be.entity.user.User;
import team_pwp.swap_be.repository.chat.ChatMessageJpaRepository;
import team_pwp.swap_be.repository.chat.ChatRoomJpaRepository;
import team_pwp.swap_be.repository.chat.EnterChatRoomJpaRepository;
import team_pwp.swap_be.repository.user.UserJpaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatService {

    private final UserJpaRepository userJpaRepository;
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ChatMessageJpaRepository chatMessageJpaRepository;
    private final EnterChatRoomJpaRepository enterChatRoomJpaRepository;


    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 채팅방에 메시지를 전송하는 서비스 1. 유저가 채팅방에 있는지 확인 2. 채팅방에 메시지 전송 3. 채팅방에 메시지 저장 4. 메시지 broadcast
     *
     * @param chatMessageRequest
     */
    public void sendMessage(ChatMessageRequest chatMessageRequest) {
        // 유저 아이디와 채팅방 아이디로 유저가 채팅방에 있는지 확인
        log.info("userId: {}, chatRoomId: {}", chatMessageRequest.userId(),
            chatMessageRequest.chatRoomId());
        if (!enterChatRoomJpaRepository.existsByChatRoomIdAndUserId(
            chatMessageRequest.chatRoomId(), chatMessageRequest.userId())) {
            throw new IllegalArgumentException("채팅방에 참가하지 않은 사용자입니다.");
        }

        // 유저와 채팅방 정보를 가져옴
        User user = userJpaRepository.findById(chatMessageRequest.userId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        ChatRoom chatRoom = chatRoomJpaRepository.findById(chatMessageRequest.chatRoomId())
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        // 새로운 ChatMessage 객체를 생성
        ChatMessage chatMessage = ChatMessage.create(chatRoom, user, chatMessageRequest.message());

        // 메시지를 데이터베이스에 저장
        chatMessageJpaRepository.save(chatMessage);

        // 메시지를 특정 주제(채팅방)로 브로드캐스트
        // 메세지는 json 형태로 전송
        messagingTemplate.convertAndSend("/topic/chat/" + chatRoom.getId(),
            ChatMessageResponse.from(chatMessage));

        log.info("메시지 전송 완료: {}", chatMessage);

    }

    public void EnterRoom(EnterRoomRequest enterRoomRequest) {
        // 유저 아이디와 채팅방 아이디로 유저가 채팅방에 있는지 확인
        if (enterChatRoomJpaRepository.existsByChatRoomIdAndUserId(
            enterRoomRequest.chatRoomId(), enterRoomRequest.userId())) {
            throw new IllegalArgumentException("이미 채팅방에 참가한 사용자입니다.");
        }

        // 유저와 채팅방 정보를 가져옴
        User user = userJpaRepository.findById(enterRoomRequest.userId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        ChatRoom chatRoom = chatRoomJpaRepository.findById(enterRoomRequest.chatRoomId())
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        // 새로운 EnterChatRoom 객체를 생성
        EnterChatRoom enterChatRoom = EnterChatRoom.create(chatRoom, user);

        // 채팅방에 유저 추가
        enterChatRoomJpaRepository.save(enterChatRoom);

        log.info("채팅방 참가 완료: {}", enterChatRoom);

        // 채팅방에 참가한 사용자에게 알림
        String message = user.getNickname() + "님이 " + chatRoom.getTitle() + " 방에 참가하였습니다.";
        messagingTemplate.convertAndSend("/topic/chat/" + chatRoom.getId(), message);
    }
}
