package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecommendedWebtoonInfoResponse {

    private List<RecommendedWebtoonInfo> webtoons;

    public RecommendedWebtoonInfoResponse(List<RecommendedWebtoonInfo> webtoons){
        this.webtoons = webtoons;
    }

    public static RecommendedWebtoonInfoResponse toResponse(List<Webtoon> webtoons){
        List<RecommendedWebtoonInfo> webtoonInfos = webtoons.stream()
                .map(RecommendedWebtoonInfo::toList)
                .collect(Collectors.toList());

        return new RecommendedWebtoonInfoResponse(webtoonInfos);
    }
}
