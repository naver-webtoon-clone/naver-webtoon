package com.naver.webtoon.webtoon.entity;

import com.naver.webtoon.common.time.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebtoonPublishingDay extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "webtoon_publishing_day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="webtoon_id")
    private Webtoon webtoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publishing_day_id")
    private PublishingDay publishingDay;

    @Builder
    public WebtoonPublishingDay(Long id, Webtoon webtoon, PublishingDay publishingDay) {
        this.id = id;
        this.webtoon = webtoon;
        this.publishingDay = publishingDay;
    }
}
