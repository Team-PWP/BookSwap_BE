package team_pwp.swap_be.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TokenDto {

    private final String grantType;
    private final String accessToken;
    private final Long accessTokenExpiresIn;
}
