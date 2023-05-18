package com.naver.webtoon.webtoon.dto.request;

import com.naver.webtoon.webtoon.entity.HashTag;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashTagRegisterRequest {

    private String name;

    public HashTag toHashTag() {
        return HashTag.builder()
                .name(name)
                .build();
    }

    public HashTagRegisterRequest(String name) {
        this.name = name;
    }
}
