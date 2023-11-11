package elegant.children.catchculture.entity.interaction;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultural_event_id")
    private CulturalEvent culturalEvent;

    @Enumerated(EnumType.STRING)
    private LikeStar likeStar;

    public static Interaction createInteraction(final User user, final CulturalEvent culturalEvent, final LikeStar likeStar) {
        return Interaction.builder()
                .user(user)
                .culturalEvent(culturalEvent)
                .likeStar(likeStar)
                .build();
    }

}
