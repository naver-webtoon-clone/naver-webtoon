package com.naver.webtoon.comment.dto.response;

import com.naver.webtoon.common.response.SliceInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentInfoSliceResponse {

    private SliceInfoResponse sliceInfo;
    private List<CommentInfo> comments;

    public CommentInfoSliceResponse(SliceInfoResponse sliceInfo, List<CommentInfo> comments) {
        this.sliceInfo = sliceInfo;
        this.comments = comments;
    }

    public static CommentInfoSliceResponse toResponse(Slice<CommentInfo> commentSlice){
        SliceInfoResponse sliceInfo = SliceInfoResponse.toResponse(commentSlice);
        List<CommentInfo> comments = commentSlice.getContent();

        return new CommentInfoSliceResponse(sliceInfo, comments);
    }
}
