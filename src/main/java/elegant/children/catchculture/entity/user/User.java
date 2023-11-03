package elegant.children.catchculture.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private int id;

    @Column(nullable = false)
    private String email;

    @Column
    private int point;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String nickname;

    private String socialId;
    @PrePersist
    public void prePersist() {
        this.point = 0;
    }

}
