package elegant.children.catchculture.repository.culturalEvent;

import java.util.Arrays;

public enum PartitionType {

    RECENT,
    LIKE,
    VIEW_COUNT,
    STAR,
    VISIT_AUTH;


    public static PartitionType of(String source) {
        return Arrays.stream(PartitionType.values())
                .filter(sortType -> sortType.name().equals(source))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정렬 타입입니다."));
    }
}
