package com.naver.webtoon.webtoon.entity;

import com.naver.webtoon.common.time.Timestamped;
import com.naver.webtoon.member.entity.Member;
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
public class InterestedWebtoon extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interested_webtoon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public InterestedWebtoon(Long id, Webtoon webtoon, Member member) {
        this.id = id;
        this.webtoon = webtoon;
        this.member = member;
    }

    public static InterestedWebtoon createInterestedWebtoon(Member member, Webtoon webtoon) {
        return InterestedWebtoon.builder()
                .member(member)
                .webtoon(webtoon)
                .build();
    }

}
