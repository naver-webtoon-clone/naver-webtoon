package com.naver.webtoon.episode.dto.response;

import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EpisodeRetrieveResponse {

    private Long episodeId;
    private String webtoonTitle;
    private String episodeTitle;
    private String content;

    @Builder
    public EpisodeRetrieveResponse(Long episodeId, String webtoonTitle, String episodeTitle, String content) {
        this.episodeId = episodeId;
        this.webtoonTitle = webtoonTitle;
        this.episodeTitle = episodeTitle;
        this.content = content;
    }

    public static EpisodeRetrieveResponse toResponse(Episode episode) {
        Webtoon webtoon = episode.getWebtoon();

        return EpisodeRetrieveResponse.builder()
                .episodeId(episode.getId())
                .webtoonTitle(webtoon.getTitle())
                .episodeTitle(episode.getTitle())
                .content(episode.getContent())
                .build();
    }
}
