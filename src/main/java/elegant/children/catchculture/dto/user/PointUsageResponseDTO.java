package elegant.children.catchculture.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class PointUsageResponseDTO {
    private int id;
    private String name;
    private String photoUrl;
    private int price;
    private String description;

    public PointUsageResponseDTO(int id, String name, String photoUrl, int price, String description){
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.price = price;
        this.description = description;

    }
}
