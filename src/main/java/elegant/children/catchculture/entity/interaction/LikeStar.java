package elegant.children.catchculture.entity.interaction;

import elegant.children.catchculture.common.constant.Classification;

public enum LikeStar {

    LIKE,
    STAR;

    public static LikeStar of(final Classification classification) {
        if (classification == Classification.LIKE) {
            return LikeStar.LIKE;
        }
        return LikeStar.STAR;
    }
}
