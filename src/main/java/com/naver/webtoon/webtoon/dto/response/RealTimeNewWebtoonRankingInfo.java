package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.common.time.TimeUtils;
import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RealTimeNewWebtoonRankingInfo {

    private Long webtoonId;
    private String title;
    private String author;
    private String thumbnail;
    private Boolean isUpdatedToday;

    @Builder
    public RealTimeNewWebtoonRankingInfo(Long webtoonId,
                                      String title,
                                      String author,
                                      String thumbnail,
                                      Boolean isUpdatedToday){
        this.webtoonId = webtoonId;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.isUpdatedToday = isUpdatedToday;
    }

    public static RealTimeNewWebtoonRankingInfo toList(Webtoon webtoon){
        Boolean isUpdatedToday = TimeUtils.isUpdatedWithin24Hours(webtoon.getUpdatedAt());

        return RealTimeNewWebtoonRankingInfo.builder()
                .webtoonId(webtoon.getId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor().getName())
                .thumbnail(webtoon.getThumbnail())
                .isUpdatedToday(isUpdatedToday)
                .build();
    }
}
