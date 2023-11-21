package elegant.children.catchculture.dto.review.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import elegant.children.catchculture.entity.review.Review;
import elegant.children.catchculture.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReviewResponseDTO {

    private String nickname;
    private String description;
    private String storedFileUrl;
    private int rating;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    public static ReviewResponseDTO of(final Review review, final User user) {
        return ReviewResponseDTO.builder()
                .nickname(user.getNickname())
                .description(review.getDescription())
                .storedFileUrl(review.getStoredFileURL())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();

    }

}
