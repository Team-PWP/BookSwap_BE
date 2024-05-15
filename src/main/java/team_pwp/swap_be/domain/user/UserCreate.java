package team_pwp.swap_be.domain.user;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    private final String email;
    private final String userName;
    private final String nickName;

    @Builder
    private UserCreate(String email, String userName, String nickName) {
        this.email = email;
        this.userName = userName;
        this.nickName = nickName;
    }

    public static UserCreate from(JsonNode jsonNode) {
        return UserCreate.builder()
            .email(jsonNode.get("kakao_account").get("email").asText())
            .userName(jsonNode.get("properties").get("nickname").asText())
            .nickName(jsonNode.get("properties").get("nickname").asText())
            .build();
    }

}
