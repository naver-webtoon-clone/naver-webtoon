package com.naver.webtoon.episode.dto;

import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeRegisterRequest {

    private String title;
    private String content;
    private String postscript;
    private Boolean isPublic;
    @Min(value = 0, message =  "필요한 쿠키 양은 0개 이상입니다.")
    private Integer neededCookieAmount;
    private LocalDate freeReleaseDate;

    public Episode toEpisode(Webtoon webtoon){
        return Episode.builder()
                .title(title)
                .content(content)
                .postscript(postscript)
                .views(0)
                .isPublic(isPublic)
                .neededCookieAmount(neededCookieAmount)
                .freeReleaseDate(freeReleaseDate)
                .webtoon(webtoon)
                .build();
    }
}
