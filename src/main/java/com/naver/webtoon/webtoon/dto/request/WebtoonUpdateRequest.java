package com.naver.webtoon.webtoon.dto.request;

import com.naver.webtoon.webtoon.entity.HashTag;
import com.naver.webtoon.webtoon.entity.PublishingDay;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.entity.WebtoonHashTag;
import com.naver.webtoon.webtoon.entity.WebtoonPublishingDay;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WebtoonUpdateRequest {

    private String title;
    private String author;
    private String description;
    private String thumbnail;
    private String serializedStatus;
    private List<String> publishingDay;
    private List<String> hashTag;

    public WebtoonPublishingDay toWebtoonPublishingDay(Webtoon webtoon, PublishingDay publishingDay) {
        return WebtoonPublishingDay.builder()
                .webtoon(webtoon)
                .publishingDay(publishingDay)
                .build();
    }

    public WebtoonHashTag toWebtoonHashTag(Webtoon webtoon, HashTag hashTag) {
        return WebtoonHashTag.builder()
                .webtoon(webtoon)
                .hashTag(hashTag)
                .build();
    }
}
