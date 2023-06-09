package com.naver.webtoon.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BestCommentInfoListResponse {

    private List<CommentInfo> comments;

    public BestCommentInfoListResponse(List<CommentInfo> comments) {
        this.comments = comments;
    }

    public static BestCommentInfoListResponse toResponse(List<CommentInfo> bestCommentList) {
        return new BestCommentInfoListResponse(bestCommentList);
    }
}
