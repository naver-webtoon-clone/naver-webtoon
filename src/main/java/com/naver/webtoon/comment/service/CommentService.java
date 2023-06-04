package com.naver.webtoon.comment.service;

import com.naver.webtoon.comment.dto.request.CommentUpdateRequest;
import com.naver.webtoon.comment.dto.request.CommentWriteRequest;
import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.comment.repository.CommentRepository;
import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.episode.repository.EpisodeRepository;
import com.naver.webtoon.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_COMMENT;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_EPISODE;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_VALID_ACCESS;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EpisodeRepository episodeRepository;

    public void writeComment(Member currentMember, Long episodeId, CommentWriteRequest request) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));

        Comment comment = request.toComment(currentMember, episode);

        commentRepository.save(comment);
    }


    public void updateComment(Member currentMember, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        throwIfMemberMismatch(currentMember, comment.getMember());

        String content = request.getContent();
        comment.update(content);
    }

    public void deleteComment(Member currentMember, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        throwIfMemberMismatch(currentMember, comment.getMember());

        commentRepository.delete(comment);
    }

    public void throwIfMemberMismatch(Member currentMember, Member findMember) {
        if (currentMember.isMismatch(findMember)) {
            throw new WebtoonException(NOT_VALID_ACCESS);
        }
    }

}
