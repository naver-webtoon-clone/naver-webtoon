package com.naver.webtoon.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReCommentInfo {

    private Long reCommentId;
    private String content;
    private String username;
    private String emotionType;
    private Long likeCount;
    private Long dislikeCount;
    private LocalDateTime createdAt;

    public ReCommentInfo(Long reCommentId, String content, String username, String emotionType, Long likeCount, Long dislikeCount, LocalDateTime createdAt) {
        this.reCommentId = reCommentId;
        this.content = content;
        this.username = username;
        this.emotionType = emotionType;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdAt = createdAt;
    }
}
