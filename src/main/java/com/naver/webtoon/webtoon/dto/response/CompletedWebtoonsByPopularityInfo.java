package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Builder;
import lombok.Getter;

import static com.naver.webtoon.webtoon.entity.enums.SerializedStatus.BREAK;

@Getter
public class CompletedWebtoonsByPopularityInfo {

    private Long webtoonId;
    private String title;
    private String author;
    private String thumbnail;
    private String isPause;
//    private String webtoonStarRating;

    @Builder
    public CompletedWebtoonsByPopularityInfo(Long webtoonId,
                                             String title,
                                             String author,
                                             String thumbnail,
                                             String isPause
//                                             String webtoonStarRating
    ){
        this.webtoonId = webtoonId;
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.isPause = isPause;
//        this.webtoonStarRating = webtoonStarRating;
    }
    public static CompletedWebtoonsByPopularityInfo toList(Webtoon webtoon){
        //TODO: webtoonStarRating 내용 임의의 수 후에 수정 필요.
//        String webtoonStarRating = "0.0F";
        String isPause = String.valueOf((webtoon.getSerializedStatus() == BREAK));


        return CompletedWebtoonsByPopularityInfo.builder()
                .webtoonId(webtoon.getId())
                .title(webtoon.getTitle())
                .author(webtoon.getAuthor().getName())
                .thumbnail(webtoon.getThumbnail())
                .isPause(isPause)
//                .webtoonStarRating(webtoonStarRating)
                .build();
    }
}
