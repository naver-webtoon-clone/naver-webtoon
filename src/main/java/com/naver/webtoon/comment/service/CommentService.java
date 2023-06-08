package com.naver.webtoon.comment.service;

import com.naver.webtoon.comment.dto.request.CommentUpdateRequest;
import com.naver.webtoon.comment.dto.request.CommentWriteRequest;
import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.comment.entity.CommentEmotion;
import com.naver.webtoon.comment.entity.enums.EmotionType;
import com.naver.webtoon.comment.repository.CommentEmotionRepository;
import com.naver.webtoon.comment.repository.CommentRepository;
import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.episode.repository.EpisodeRepository;
import com.naver.webtoon.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.naver.webtoon.common.exception.ErrorCode.DUPLICATE_COMMENT_EMOTION;
import static com.naver.webtoon.common.exception.ErrorCode.EXIST_DIFFERENT_COMMENT_EMOTION;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_COMMENT;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_COMMENT_EMOTION;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_EPISODE;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_VALID_ACCESS;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EpisodeRepository episodeRepository;
    private final CommentEmotionRepository commentEmotionRepository;

    @Transactional
    public void writeComment(Member currentMember, Long episodeId, CommentWriteRequest request) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));

        Comment comment = request.toComment(currentMember, episode);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Member currentMember, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        throwIfMemberMismatch(currentMember, comment.getMember());

        String content = request.getContent();
        comment.update(content);
    }

    @Transactional
    public void deleteComment(Member currentMember, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        throwIfMemberMismatch(currentMember, comment.getMember());

        commentRepository.delete(comment);
    }

    @Transactional
    public void registerCommentLike(Member currentMember, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        Long memberId = currentMember.getId();
        CommentEmotion commentLike = CommentEmotion.createCommentLike(memberId, comment);

        throwIfDuplicatedEmotion(memberId, comment);
        commentEmotionRepository.save(commentLike);
    }

    @Transactional
    public void deleteCommentLike(Member currentMember, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        Long memberId = currentMember.getId();
        CommentEmotion commentEmotion = commentEmotionRepository.findByMemberIdAndComment(memberId, comment).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT_EMOTION));

        throwIfEmotionDifferentFromLike(commentEmotion.getType());

        commentEmotionRepository.delete(commentEmotion);
    }

    @Transactional
    public void registerCommentDislike(Member currentMember, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        Long memberId = currentMember.getId();
        CommentEmotion commentDislike = CommentEmotion.createCommentDislike(memberId, comment);

        throwIfDuplicatedEmotion(memberId, comment);
        commentEmotionRepository.save(commentDislike);
    }

    @Transactional
    public void deleteCommentDislike(Member currentMember, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        Long memberId = currentMember.getId();
        CommentEmotion commentEmotion = commentEmotionRepository.findByMemberIdAndComment(memberId, comment).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT_EMOTION));

        throwIfEmotionDifferentFromDislike(commentEmotion.getType());

        commentEmotionRepository.delete(commentEmotion);
    }

    private void throwIfMemberMismatch(Member currentMember, Member findMember) {
        if (currentMember.isMismatch(findMember)) {
            throw new WebtoonException(NOT_VALID_ACCESS);
        }
    }

    private void throwIfDuplicatedEmotion(Long memberId, Comment comment) {
        if (commentEmotionRepository.existsByMemberIdAndComment(memberId, comment)) {
            throw new WebtoonException(DUPLICATE_COMMENT_EMOTION);
        }
    }

    private void throwIfEmotionDifferentFromLike(EmotionType type) {
        if (!type.isLike()) {
            throw new WebtoonException(EXIST_DIFFERENT_COMMENT_EMOTION);
        }
    }

    private void throwIfEmotionDifferentFromDislike(EmotionType type) {
        if (!type.isDislike()) {
            throw new WebtoonException(EXIST_DIFFERENT_COMMENT_EMOTION);
        }
    }

}
