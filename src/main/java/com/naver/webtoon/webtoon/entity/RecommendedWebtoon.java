package com.naver.webtoon.webtoon.entity;

import com.naver.webtoon.common.time.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedWebtoon extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommended_webtoon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @Builder
    public RecommendedWebtoon(Long id, Webtoon webtoon){
        this.id = id;
        this.webtoon = webtoon;
    }

    public static RecommendedWebtoon createRecommendedWebtoon(Webtoon webtoon){
        return RecommendedWebtoon.builder()
                .webtoon(webtoon)
                .build();
    }
}
