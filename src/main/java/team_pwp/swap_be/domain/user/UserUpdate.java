package team_pwp.swap_be.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUpdate {

    private final String nickname;
    private final String username;

}
