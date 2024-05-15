package team_pwp.swap_be.auth.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.auth.dto.TokenDto;
import team_pwp.swap_be.auth.service.OAuthService;
import team_pwp.swap_be.auth.service.SecurityService;
import team_pwp.swap_be.domain.user.UserCreate;
import team_pwp.swap_be.entity.User;
import team_pwp.swap_be.service.user.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;
    private final SecurityService securityService;
    private final UserService userService;

    @GetMapping("/oauth2")
    public ResponseEntity<Void> socialLogin(@RequestParam("code") String code) {
        UserCreate userInfo = oAuthService.getUserInfo(code);
        log.info("userInfo: {}", userInfo);
        //null일때 exception 처리
        Optional<User> user = Optional.ofNullable(userService.SignIn(userInfo).orElseThrow(() ->
            new IllegalArgumentException("로그인 실패")));
        log.info("user: {}", user.get().getId());
        TokenDto tokenDto = securityService.generateTokenDto(user.get().getId());
        HttpHeaders headers = securityService.setTokenHeaders(tokenDto);
        log.info("로그인 성공");

        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headers)
            .build();
    }

}
