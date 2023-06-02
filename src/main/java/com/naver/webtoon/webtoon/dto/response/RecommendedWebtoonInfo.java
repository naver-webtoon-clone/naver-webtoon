package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.common.time.TimeUtils;
import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Builder;
import lombok.Getter;

import static com.naver.webtoon.webtoon.entity.enums.SerializedStatus.BREAK;

@Getter
public class RecommendedWebtoonInfo {
    private Long webtoonId;
    private String title;
    private String author;
    private String thumbnail;
    private String latestEpisodeTitle;
    private Float webtoonStarRating;

    @Builder
    public RecommendedWebtoonInfo(Long webtoonId,
                                  String title,
                                  String author,
                                  String thumbnail,
                                  String latestEpisodeTitle,
                                  Float webtoonStarRating){
        this.webtoonId = webtoonId;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.latestEpisodeTitle = latestEpisodeTitle;
        this.webtoonStarRating = webtoonStarRating;
    }

    //TODO: latestEpisodeTitle episode 테이블에서 조회후 입력 필요,
    // webtoonStarRating episode 테이블 구현후 수정 필요.
    public static RecommendedWebtoonInfo toList(Webtoon webtoon){
        Float webtoonStarRating = 0.0F;
        String latestEpisodeTitle = "latestEpisodeTitle";

        return RecommendedWebtoonInfo.builder()
                .webtoonId(webtoon.getId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor().getName())
                .thumbnail(webtoon.getThumbnail())
                .latestEpisodeTitle(latestEpisodeTitle)
                .webtoonStarRating(webtoonStarRating)
                .build();
    }
}
