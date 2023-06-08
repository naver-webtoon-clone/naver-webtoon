package com.naver.webtoon.comment.dto.request;

import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentWriteRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public Comment toComment(Member member, Episode episode) {
        return Comment.builder()
                .content(content)
                .member(member)
                .episode(episode)
                .build();
    }
}
