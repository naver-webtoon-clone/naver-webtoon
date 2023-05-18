package com.naver.webtoon.webtoon.dto.request;

import com.naver.webtoon.webtoon.entity.PublishingDay;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PublishingDayRegisterRequest {

    private DayOfTheWeek dayOfTheWeek;

    public PublishingDay toPublishingDay() {
        return PublishingDay.builder()
                .dayOfTheWeek(dayOfTheWeek)
                .build();
    }

    public PublishingDayRegisterRequest(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }
}
