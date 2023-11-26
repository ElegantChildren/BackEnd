package elegant.children.catchculture.dto.review.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequestDTO {

    private String description;
    private int rating;
}
