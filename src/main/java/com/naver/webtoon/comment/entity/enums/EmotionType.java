package com.naver.webtoon.comment.entity.enums;

public enum EmotionType {

    LIKE,
    DISLIKE
    ;

    public boolean isLike() {
        return this == LIKE;
    }

    public boolean isDislike() {
        return this == DISLIKE;
    }
}
