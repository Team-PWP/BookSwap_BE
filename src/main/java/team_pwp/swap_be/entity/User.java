package team_pwp.swap_be.entity;

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
    private String userName;

    /**
     * default = userName
     */
    @Column(unique = true)
    private String nickname;

    @Builder
    private User(String email, String userName, String nickname) {
        this.email = email;
        this.userName = userName;
        this.nickname = nickname;
    }

    public static User createUser(UserCreate userCreate) {
        return User.builder()
            .email(userCreate.getEmail())
            .userName(userCreate.getUserName())
            .nickname(userCreate.getNickName())
            .build();
    }

    public void modifyNickname() {
        this.nickname = this.userName;
    }
}
