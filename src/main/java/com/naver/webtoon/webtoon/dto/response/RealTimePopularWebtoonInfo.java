package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.common.time.TimeUtils;
import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RealTimePopularWebtoonInfo {
    private Long webtoonId;
    private String title;
    private String author;
    private String thumbnail;
    private Boolean isUpdatedToday;
    private Float webtoonStarRating;

    @Builder
    public RealTimePopularWebtoonInfo(Long webtoonId,
                                      String title,
                                      String author,
                                      String thumbnail,
                                      Boolean isUpdatedToday,
                                      Float webtoonStarRating){
        this.webtoonId = webtoonId;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.isUpdatedToday = isUpdatedToday;
        this.webtoonStarRating = webtoonStarRating;
    }

    public static RealTimePopularWebtoonInfo toList(Webtoon webtoon){
        Boolean isUpdatedToday = TimeUtils.isUpdatedWithin24Hours(webtoon.getUpdatedAt());
        //TODO: webtoonStarRating 내용 임의의 수 후에 수정 필요.
        Float webtoonStarRating = 0.0F;

        return RealTimePopularWebtoonInfo.builder()
                .webtoonId(webtoon.getId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor().getName())
                .thumbnail(webtoon.getThumbnail())
                .isUpdatedToday(isUpdatedToday)
                .webtoonStarRating(webtoonStarRating)
                .build();
    }
}
