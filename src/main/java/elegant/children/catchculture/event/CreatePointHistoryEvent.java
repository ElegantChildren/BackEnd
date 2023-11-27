package elegant.children.catchculture.event;

import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePointHistoryEvent {

    private PointChange pointChange;
    private User user;
}
