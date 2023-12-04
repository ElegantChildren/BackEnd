package elegant.children.catchculture.dto.review.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ReviewRatingResponseDTO {


    private double avgRating = 0;
    private long countFive = 0;
    private long countFour = 0;
    private long countThree = 0;
    private long countTwo = 0;
    private long countOne = 0;

    public void setAvgRating() {
        if(countFive + countFour + countThree + countTwo + countOne == 0) {
            return;
        }
        this.avgRating = (double)(countFive * 5 + countFour * 4 + countThree * 3 + countTwo * 2 + countOne) / (countFive + countFour + countThree + countTwo + countOne);
        this.avgRating = Math.round(this.avgRating * 100) / 100.0;
    }
}
