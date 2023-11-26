package elegant.children.catchculture.event.review;


import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateReviewEvent {

    private PointChange pointChange;
    private User user;

}
