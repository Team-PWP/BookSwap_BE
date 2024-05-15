package team_pwp.swap_be.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import team_pwp.swap_be.domain.user.UserCreate;
import team_pwp.swap_be.entity.User;
import team_pwp.swap_be.repository.UserJpaRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public Optional<User> SignIn(UserCreate userInfo) {
        //email로 회원가입이 되어있는지 확인
        Optional<User> user = userJpaRepository.findByEmail(userInfo.getEmail());
        log.info("user: {}", user);
        //회원가입이 되어있지 않다면 회원가입 진행
        if (!user.isPresent()) {
            userJpaRepository.save(User.createUser(userInfo));
            log.info("회원가입 완료");
            return userJpaRepository.findByEmail(userInfo.getEmail());
        }
        return user;
    }
}
