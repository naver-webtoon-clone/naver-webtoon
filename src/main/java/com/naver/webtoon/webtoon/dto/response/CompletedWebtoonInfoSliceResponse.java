package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.common.response.SliceInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
public class CompletedWebtoonInfoSliceResponse {
    private SliceInfoResponse sliceInfoResponse;
    private List<CompletedWebtoonsByPopularityInfo> completedWebtoonsByPopularityInfos;

    public CompletedWebtoonInfoSliceResponse(SliceInfoResponse sliceInfoResponse,
                                             List<CompletedWebtoonsByPopularityInfo> completedWebtoonsByPopularityInfos){
        this.sliceInfoResponse = sliceInfoResponse;
        this.completedWebtoonsByPopularityInfos = completedWebtoonsByPopularityInfos;
    }

    public static CompletedWebtoonInfoSliceResponse toResponse(Slice<CompletedWebtoonsByPopularityInfo> completedWebtoonSlice){
        SliceInfoResponse sliceInfo = SliceInfoResponse.toResponse(completedWebtoonSlice);
        List<CompletedWebtoonsByPopularityInfo> completedWebtoons = completedWebtoonSlice.getContent();

        return new CompletedWebtoonInfoSliceResponse(sliceInfo, completedWebtoons);
    }
}
