package com.naver.webtoon.webtoon.dto.response;

import com.naver.webtoon.common.response.SliceInfoResponse;
import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
public class WebtoonInfoSliceResponse {
    private SliceInfoResponse sliceInfoResponse;
    private List<Webtoon> webtoonInfos;

    public WebtoonInfoSliceResponse(SliceInfoResponse sliceInfoResponse,
                                    List<Webtoon> webtoonInfos){
        this.sliceInfoResponse = sliceInfoResponse;
        this.webtoonInfos = webtoonInfos;
    }

    public static WebtoonInfoSliceResponse toResponse(Slice<Webtoon> webtoonInfoSlice){
        SliceInfoResponse sliceInfo = SliceInfoResponse.toResponse(webtoonInfoSlice);
        List<Webtoon> webtoonContents = webtoonInfoSlice.getContent();

        return new WebtoonInfoSliceResponse(sliceInfo, webtoonContents);
    }
}
