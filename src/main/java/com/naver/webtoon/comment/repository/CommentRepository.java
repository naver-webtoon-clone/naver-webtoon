package com.naver.webtoon.comment.repository;

import com.naver.webtoon.comment.dto.response.CommentInfo;
import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.episode.entity.Episode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT new com.naver.webtoon.comment.dto.response.CommentInfo(" +
            "comment.id, " +
            "comment.content, " +
            "comment.member.username, " +
            "CASE WHEN commentEmotion.type = 'LIKE' THEN 'LIKE' " +
            "WHEN commentEmotion.type = 'DISLIKE' THEN 'DISLIKE' " +
            "ELSE 'EMOTIONLESS' END, " +
            "comment.likeCount, " +
            "comment.dislikeCount, " +
            "(SELECT COUNT(reComment) FROM ReComment reComment WHERE reComment.comment = comment), " +
            "comment.createdAt) " +
            "FROM Comment comment " +
            "LEFT JOIN CommentEmotion commentEmotion ON commentEmotion.comment = comment AND commentEmotion.memberId = :currentMemberId " +
            "WHERE comment.episode = :episode " +
            "ORDER BY comment.likeCount DESC")
    Slice<CommentInfo> findBestCommentListByEpisodeWhenLogin(@Param("episode") Episode episode, @Param("currentMemberId") Long currentMemberId, Pageable pageable);

    @Query(value = "SELECT new com.naver.webtoon.comment.dto.response.CommentInfo(" +
            "comment.id, " +
            "comment.content, " +
            "comment.member.username, " +
            "'EMOTIONLESS', " +
            "comment.likeCount, " +
            "comment.dislikeCount, " +
            "(SELECT COUNT(reComment) FROM ReComment reComment WHERE reComment.comment = comment), " +
            "comment.createdAt) " +
            "FROM Comment comment " +
            "LEFT JOIN CommentEmotion commentEmotion ON commentEmotion.comment = comment " +
            "WHERE comment.episode = :episode " +
            "ORDER BY comment.likeCount DESC")
    Slice<CommentInfo> findBestCommentListByEpisodeWhenNonLogin(@Param("episode") Episode episode, Pageable pageable);

    @Modifying
    @Query("UPDATE Comment comment SET comment.likeCount = comment.likeCount + 1 WHERE comment = :comment")
    void increaseLikeCountAtomically(@Param("comment") Comment comment);

    @Modifying
    @Query("UPDATE Comment comment SET comment.likeCount = comment.likeCount - 1 WHERE comment = :comment")
    void decreaseLikeCountAtomically(@Param("comment") Comment comment);

    @Modifying
    @Query("UPDATE Comment comment SET comment.dislikeCount = comment.dislikeCount + 1 WHERE comment = :comment")
    void increaseDislikeCountAtomically(@Param("comment") Comment comment);

    @Modifying
    @Query("UPDATE Comment comment SET comment.dislikeCount = comment.dislikeCount - 1 WHERE comment = :comment")
    void decreaseDislikeCountAtomically(@Param("comment") Comment comment);

    @Query(value = "SELECT new com.naver.webtoon.comment.dto.response.CommentInfo(" +
            "comment.id, " +
            "comment.content, " +
            "comment.member.username, " +
            "CASE WHEN commentEmotion.type = 'LIKE' THEN 'LIKE' " +
            "WHEN commentEmotion.type = 'DISLIKE' THEN 'DISLIKE' " +
            "ELSE 'EMOTIONLESS' END, " +
            "comment.likeCount, " +
            "comment.dislikeCount, " +
            "(SELECT COUNT(reComment) FROM ReComment reComment WHERE reComment.comment = comment), " +
            "comment.createdAt) " +
            "FROM Comment comment " +
            "LEFT JOIN CommentEmotion commentEmotion ON commentEmotion.comment = comment AND commentEmotion.memberId = :currentMemberId " +
            "WHERE comment.episode = :episode " +
            "ORDER BY comment.createdAt")
    Slice<CommentInfo> findSliceByEpisodeWhenLogin(Episode episode, Long currentMemberId, Pageable pageable);

    @Query(value = "SELECT new com.naver.webtoon.comment.dto.response.CommentInfo(" +
            "comment.id, " +
            "comment.content, " +
            "comment.member.username, " +
            "'EMOTIONLESS', " +
            "comment.likeCount, " +
            "comment.dislikeCount, " +
            "(SELECT COUNT(reComment) FROM ReComment reComment WHERE reComment.comment = comment), " +
            "comment.createdAt) " +
            "FROM Comment comment " +
            "LEFT JOIN CommentEmotion commentEmotion ON commentEmotion.comment = comment " +
            "WHERE comment.episode = :episode " +
            "ORDER BY comment.createdAt")
    Slice<CommentInfo> findSliceByEpisodeWhenNonLogin(Episode episode, Pageable pageable);
}
