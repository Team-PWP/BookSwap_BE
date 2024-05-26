package team_pwp.swap_be.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import team_pwp.swap_be.auth.dto.TokenDto;
import team_pwp.swap_be.auth.jwt.JwtProvider;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final JwtProvider jwtProvider;
    private final String prefix = "Bearer ";

    public TokenDto generateTokenDto(Long userId) {

        TokenDto tokenDto = jwtProvider.generateTokenDto(userId);

        return tokenDto;
    }

    public HttpHeaders setTokenHeaders(TokenDto tokenDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", prefix + tokenDto.getAccessToken());

        return headers;
    }


}