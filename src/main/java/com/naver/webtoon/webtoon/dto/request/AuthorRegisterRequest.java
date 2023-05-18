package com.naver.webtoon.webtoon.dto.request;

import com.naver.webtoon.webtoon.entity.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorRegisterRequest {

    private String name;


    public Author toAuthor() {
        return Author.builder()
                .name(name)
                .build();
    }

    public AuthorRegisterRequest(String name) {
        this.name = name;
    }
}
