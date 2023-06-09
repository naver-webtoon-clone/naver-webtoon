package com.naver.webtoon.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentInfo {

    private Long commentId;
    private String content;
    private String username;
    private String emotionType;
    private Long likeCount;
    private Long dislikeCount;
    private Long reCommentCount;
    private LocalDateTime createdAt;

    public CommentInfo(Long commentId, String content, String username, String emotionType, Long likeCount, Long dislikeCount, Long reCommentCount, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.username = username;
        this.emotionType = emotionType;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.reCommentCount = reCommentCount;
        this.createdAt = createdAt;
    }
}
