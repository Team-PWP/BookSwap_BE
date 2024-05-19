package team_pwp.swap_be.domain.user;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    private final String email;
    private final String username;
    private final String nickname;

    @Builder
    private UserCreate(String email, String username, String nickname) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
    }

    public static UserCreate from(JsonNode jsonNode) {
        return UserCreate.builder()
            .email(jsonNode.get("kakao_account").get("email").asText())
            .username(jsonNode.get("properties").get("nickname").asText())
            .nickname(jsonNode.get("properties").get("nickname").asText())
            .build();
    }

}
