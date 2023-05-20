package com.naver.webtoon.webtoon.entity;


import com.naver.webtoon.common.time.Timestamped;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PublishingDay extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publishing_day_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek;

    @Builder
    public PublishingDay(Long id, DayOfTheWeek dayOfTheWeek) {
        this.id = id;
        this.dayOfTheWeek = dayOfTheWeek;
    }
}
