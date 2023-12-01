package elegant.children.catchculture.dto.user;


import elegant.children.catchculture.entity.pointhistory.PointHistory;
import elegant.children.catchculture.entity.user.User;
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
//    public PointHistoryResponseDTO(int id,LocalDateTime createdAt, String description, int pointChange, int userId) {
//        this.id = id;
//        this.createdAt = createdAt;
//        this.description = description;
//        this.pointChange = pointChange;
//        this.userId = userId;
//
//    }

}
