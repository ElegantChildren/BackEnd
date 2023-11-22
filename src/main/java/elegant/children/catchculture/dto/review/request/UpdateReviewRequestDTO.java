package elegant.children.catchculture.dto.review.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateReviewRequestDTO {

    private int reviewId;
    private String description;
}
