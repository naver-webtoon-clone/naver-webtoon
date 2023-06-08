package com.naver.webtoon.comment.service;

import com.naver.webtoon.comment.dto.request.ReCommentUpdateRequest;
import com.naver.webtoon.comment.dto.request.ReCommentWriteRequest;
import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.comment.entity.ReComment;
import com.naver.webtoon.comment.entity.ReCommentEmotion;
import com.naver.webtoon.comment.entity.enums.EmotionType;
import com.naver.webtoon.comment.repository.CommentRepository;
import com.naver.webtoon.comment.repository.ReCommentEmotionRepository;
import com.naver.webtoon.comment.repository.ReCommentRepository;
import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.naver.webtoon.common.exception.ErrorCode.DUPLICATE_COMMENT_EMOTION;
import static com.naver.webtoon.common.exception.ErrorCode.EXIST_DIFFERENT_COMMENT_EMOTION;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_COMMENT;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_COMMENT_EMOTION;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_RE_COMMENT;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_VALID_ACCESS;

@Service
@RequiredArgsConstructor
public class ReCommentService {

    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;
    private final ReCommentEmotionRepository reCommentEmotionRepository;

    @Transactional
    public void writeReComment(Member currentMember, Long commentId, ReCommentWriteRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        ReComment reComment = request.toReComment(currentMember, comment);

        reCommentRepository.save(reComment);
    }

    @Transactional
    public void updateReComment(Member currentMember, Long reCommentId, ReCommentUpdateRequest request) {
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_RE_COMMENT));

        throwIfMemberMismatch(currentMember, reComment.getMember());

        String content = request.getContent();
        reComment.update(content);
    }

    @Transactional
    public void deleteReComment(Member currentMember, Long reCommentId) {
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_RE_COMMENT));

        throwIfMemberMismatch(currentMember, reComment.getMember());

        reCommentRepository.delete(reComment);
    }

    @Transactional
    public void registerReCommentLike(Member currentMember, Long reCommentId) {
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_RE_COMMENT));

        Long memberId = currentMember.getId();
        ReCommentEmotion reCommentEmotion = ReCommentEmotion.createReCommentLike(memberId, reComment);

        throwIfDuplicatedEmotion(memberId, reComment);
        reCommentEmotionRepository.save(reCommentEmotion);
    }

    @Transactional
    public void deleteReCommentLike(Member currentMember, Long reCommentId) {
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_RE_COMMENT));

        Long memberId = currentMember.getId();
        ReCommentEmotion recommentEmotion = reCommentEmotionRepository.findByMemberIdAndReComment(memberId, reComment).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT_EMOTION));

        throwIfEmotionDifferentFromLike(recommentEmotion.getType());

        reCommentEmotionRepository.delete(recommentEmotion);
    }

    @Transactional
    public void registerReCommentDislike(Member currentMember, Long reCommentId) {
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_RE_COMMENT));

        Long memberId = currentMember.getId();
        ReCommentEmotion reCommentDislike = ReCommentEmotion.createReCommentDislike(memberId, reComment);

        throwIfDuplicatedEmotion(memberId, reComment);
        reCommentEmotionRepository.save(reCommentDislike);
    }

    @Transactional
    public void deleteReCommentDislike(Member currentMember, Long reCommentId) {
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_RE_COMMENT));

        Long memberId = currentMember.getId();
        ReCommentEmotion recommentEmotion = reCommentEmotionRepository.findByMemberIdAndReComment(memberId, reComment).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT_EMOTION));

        throwIfEmotionDifferentFromDislike(recommentEmotion.getType());

        reCommentEmotionRepository.delete(recommentEmotion);
    }

    private void throwIfMemberMismatch(Member currentMember, Member findMember) {
        if (currentMember.isMismatch(findMember)) {
            throw new WebtoonException(NOT_VALID_ACCESS);
        }
    }

    private void throwIfDuplicatedEmotion(Long memberId, ReComment reComment) {
        if (reCommentEmotionRepository.existsByMemberIdAndReComment(memberId, reComment)) {
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

