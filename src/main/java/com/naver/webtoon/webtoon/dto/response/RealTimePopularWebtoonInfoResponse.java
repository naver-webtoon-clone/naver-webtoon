package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RealTimePopularWebtoonInfoResponse {
    private List<RealTimePopularWebtoonInfo> webtoons;

    public RealTimePopularWebtoonInfoResponse(List<RealTimePopularWebtoonInfo> webtoons){
        this.webtoons = webtoons;
    }

    public static RealTimePopularWebtoonInfoResponse toRealTimeResponse(List<Webtoon> webtoons){
        List<RealTimePopularWebtoonInfo> webtoonInfos = webtoons.stream()
                .map(RealTimePopularWebtoonInfo::toList)
                .collect(Collectors.toList());

        return new RealTimePopularWebtoonInfoResponse(webtoonInfos);
    }
}
