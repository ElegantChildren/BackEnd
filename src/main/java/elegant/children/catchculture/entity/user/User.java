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

}
