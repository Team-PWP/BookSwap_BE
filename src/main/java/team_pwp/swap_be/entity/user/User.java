package team_pwp.swap_be.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_pwp.swap_be.domain.user.UserCreate;
import team_pwp.swap_be.domain.user.UserUpdate;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    /**
     * default = userName
     */
    @Column(unique = true)
    private String nickname;

    @Builder
    private User(String email, String username, String nickname) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
    }

    public static User createUser(UserCreate userCreate) {
        return User.builder()
            .email(userCreate.getEmail())
            .username(userCreate.getUsername())
            .nickname(userCreate.getNickname())
            .build();
    }

    /**
     * 유저 정보 수정
     */
    public User updateUser(UserUpdate userUpdate) {
        this.nickname = userUpdate.getNickname();
        return this;
    }
}
