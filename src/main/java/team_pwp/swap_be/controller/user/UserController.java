package team_pwp.swap_be.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team_pwp.swap_be.dto.user.response.UserInfoResponse;
import team_pwp.swap_be.service.user.UserService;

@Tag(name = "유저", description = "유저 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회")
    @GetMapping("api/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserInfoResponse> getUserInfo(Principal principal) {
        return ResponseEntity.ok(userService.getUserInfo(Long.parseLong(principal.getName())));
    }

//    @Operation(summary = "유저 닉네임 수정", description = "유저 닉네임 수정")
//    @PutMapping("api/user/nickname")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<UserInfoResponse> updateNickname(@RequestBody Principal principal) {
//        return ResponseEntity.ok(userService.modifyNickname(Long.parseLong(principal.getName()));
//    }
}
