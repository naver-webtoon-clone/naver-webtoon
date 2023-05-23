package com.naver.webtoon.episode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeUpdateRequest {

    private String title;
    private String content;
    private String postscript;
    private Boolean isPublic;
    @Min(value = 0, message =  "필요한 쿠키 양은 0개 이상입니다.")
    private Integer neededCookieAmount;
    private LocalDate freeReleaseDate;
}
