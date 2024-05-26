package team_pwp.swap_be.repository.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import team_pwp.swap_be.entity.user.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
