package com.naver.webtoon.webtoon.entity;

import com.naver.webtoon.common.time.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedWebtoon extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommended_webtoon_id")
    private Long id;

    @OneToMany(mappedBy = "recommendedWebtoon")
    List<Webtoon> webtoons = new ArrayList<>();

    @Builder
    public RecommendedWebtoon(Long id){
        this.id = id;
    }

}
