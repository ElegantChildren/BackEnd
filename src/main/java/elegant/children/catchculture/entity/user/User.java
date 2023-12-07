package elegant.children.catchculture.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User {

    @Id @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String storedFileUrl;

    @Column
    private int point;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(nullable = false)
    private String nickname;

    @PrePersist
    public void prePersist() {
        this.point = 0;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String storedFileUrl) {
        this.storedFileUrl = storedFileUrl;
    }


    public void updatePoint(int point) {
        this.point += point;
    }
}
