package team_pwp.swap_be.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import team_pwp.swap_be.websocket.handler.MyWebSocketHandler;

@Configuration
@Slf4j
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final MyWebSocketHandler chatPreHandler;
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(chatPreHandler);
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 수 있는 WebSocket 엔드포인트를 등록
        registry.addEndpoint("/chat").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/chat")
            .setAllowedOriginPatterns("*");
        log.info("WebSocket endpoint 등록");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // "/topic"과 "/queue" 경로를 메시지 브로커가 관리하도록 설정
        registry.enableSimpleBroker("/topic", "/queue");
        // 애플리케이션에서 처리할 메시지 경로의 접두사를 "/app"으로 설정
        registry.setApplicationDestinationPrefixes("/app");
    }
}

