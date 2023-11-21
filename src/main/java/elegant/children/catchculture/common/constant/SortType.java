package elegant.children.catchculture.common.constant;

import java.util.Arrays;

public enum SortType {

    RECENT,
    LIKE,
    VIEW_COUNT;


    public static SortType of(String source) {
        return Arrays.stream(SortType.values())
                .filter(sortType -> sortType.name().equals(source))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정렬 타입입니다."));
    }
}
