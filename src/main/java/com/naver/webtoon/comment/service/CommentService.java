package com.naver.webtoon.comment.service;

import com.naver.webtoon.comment.dto.request.CommentUpdateRequest;
import com.naver.webtoon.comment.dto.request.CommentWriteRequest;
import com.naver.webtoon.comment.dto.response.BestCommentInfoListResponse;
import com.naver.webtoon.comment.dto.response.CommentInfo;
import com.naver.webtoon.comment.dto.response.ReCommentInfo;
import com.naver.webtoon.comment.dto.response.ReCommentInfoSliceResponse;
import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.comment.entity.CommentEmotion;
import com.naver.webtoon.comment.entity.enums.EmotionType;
import com.naver.webtoon.comment.repository.CommentEmotionRepository;
import com.naver.webtoon.comment.repository.CommentRepository;
import com.naver.webtoon.comment.repository.ReCommentRepository;
import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.episode.repository.EpisodeRepository;
import com.naver.webtoon.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final ReCommentRepository reCommentRepository;

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
        commentRepository.increaseLikeCountAtomically(comment);
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

        commentRepository.decreaseLikeCountAtomically(comment);
        commentEmotionRepository.delete(commentEmotion);
    }

    @Transactional
    public void registerCommentDislike(Member currentMember, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));

        Long memberId = currentMember.getId();
        CommentEmotion commentDislike = CommentEmotion.createCommentDislike(memberId, comment);

        throwIfDuplicatedEmotion(memberId, comment);

        commentRepository.increaseDislikeCountAtomically(comment);
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

        commentRepository.decreaseDislikeCountAtomically(comment);
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

    public ReCommentInfoSliceResponse retrieveReCommentWhenLogin(Member currentMember, Long commentId, int page) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        Slice<ReCommentInfo> reCommentSlice = reCommentRepository.findSliceByCommentWhenLogin(comment, currentMember.getId(), pageRequest);

        return ReCommentInfoSliceResponse.toResponse(reCommentSlice);
    }

    public ReCommentInfoSliceResponse retrieveReCommentWhenNonLogin(Long commentId, int page) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_COMMENT));
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        Slice<ReCommentInfo> reCommentSlice = reCommentRepository.findSliceByCommentWhenNonLogin(comment, pageRequest);

        return ReCommentInfoSliceResponse.toResponse(reCommentSlice);
    }

    public BestCommentInfoListResponse retrieveBestCommentWhenLogin(Member currentMember, Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));
        int start = 0;
        int size = 10;
        PageRequest limitTen = PageRequest.of(start, size);
        List<CommentInfo> bestCommentList = commentRepository.findBestCommentListByEpisodeWhenLogin(episode, currentMember.getId(), limitTen).getContent();

        return BestCommentInfoListResponse.toResponse(bestCommentList);
    }

    public BestCommentInfoListResponse retrieveBestCommentWhenNonLogin(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));
        int start = 0;
        int size = 10;
        PageRequest limitTen = PageRequest.of(start, size);
        List<CommentInfo> bestCommentList = commentRepository.findBestCommentListByEpisodeWhenNonLogin(episode, limitTen).getContent();

        return BestCommentInfoListResponse.toResponse(bestCommentList);
    }

}
