package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RealTimeNewWebtoonRankingInfoResponse {
    private List<RealTimeNewWebtoonRankingInfo> webtoons;

    public RealTimeNewWebtoonRankingInfoResponse(List<RealTimeNewWebtoonRankingInfo> webtoons){
        this.webtoons = webtoons;
    }

    public static RealTimeNewWebtoonRankingInfoResponse toResponse(List<Webtoon> webtoons){
        List<RealTimeNewWebtoonRankingInfo> webtoonInfos = webtoons.stream()
                .map(RealTimeNewWebtoonRankingInfo::toList)
                .collect(Collectors.toList());

        return new RealTimeNewWebtoonRankingInfoResponse(webtoonInfos);
    }
}
