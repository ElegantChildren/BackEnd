package elegant.children.catchculture.event;

import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCulturalEvent {

    private CulturalEventDetail culturalEventDetail;
    private User user;
    private PointChange pointChange;
}
