package elegant.children.catchculture.dto.review.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReviewDTO {

    private int id;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String description;
    private List<String> reviewStoredFileUrl;
    private int rating;
    private String culturalEventTitle;
    private List<String> eventStoredFileUrl;

    public ReviewDTO(int id, String nickName, LocalDateTime createdAt, String description, List<String> reviewStoredFileUrl, int rating, String culturalEventTitle, List<String> eventStoredFileUrl) {
        this.id = id;
        this.nickName = nickName;
        this.createdAt = createdAt;
        this.description = description;
        this.reviewStoredFileUrl = reviewStoredFileUrl;
        this.rating = rating;
        this.culturalEventTitle = culturalEventTitle;
        this.eventStoredFileUrl = eventStoredFileUrl;
    }
}
