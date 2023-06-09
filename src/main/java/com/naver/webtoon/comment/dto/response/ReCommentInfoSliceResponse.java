package com.naver.webtoon.comment.dto.response;

import com.naver.webtoon.common.response.SliceInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReCommentInfoSliceResponse {

    private SliceInfoResponse sliceInfo;
    private List<ReCommentInfo> reComments;

    public ReCommentInfoSliceResponse(SliceInfoResponse sliceInfo, List<ReCommentInfo> reComments) {
        this.sliceInfo = sliceInfo;
        this.reComments = reComments;
    }

    public static ReCommentInfoSliceResponse toResponse(Slice<ReCommentInfo> reCommentSlice){
        SliceInfoResponse sliceInfo = SliceInfoResponse.toResponse(reCommentSlice);
        List<ReCommentInfo> reComments = reCommentSlice.getContent();

        return new ReCommentInfoSliceResponse(sliceInfo, reComments);
    }
}
