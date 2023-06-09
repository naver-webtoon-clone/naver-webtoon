package com.naver.webtoon.comment.dto.request;

import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.comment.entity.ReComment;
import com.naver.webtoon.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReCommentWriteRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public ReComment toReComment(Member member, Comment comment) {
        return ReComment.builder()
                .content(content)
                .member(member)
                .comment(comment)
                .build();
    }
}
