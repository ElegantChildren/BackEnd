package elegant.children.catchculture.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PointHistoryResponseDTO {
    private int id;
    private LocalDateTime createdAt;
    private String description;
    private int pointChange;
    private int userId;
}
