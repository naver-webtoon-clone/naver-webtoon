package com.naver.webtoon.comment.repository;

import com.naver.webtoon.comment.dto.response.ReCommentInfo;
import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.comment.entity.ReComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    @Query("SELECT new com.naver.webtoon.comment.dto.response.ReCommentInfo(reComment.id, reComment.content, reComment.member.username, " +
            "CASE WHEN reCommentEmotion.type = 'LIKE' THEN 'LIKE' WHEN reCommentEmotion.type = 'DISLIKE' THEN 'DISLIKE' ELSE 'EMOTIONLESS' END, " +
            "(SELECT COUNT(emotion) FROM ReCommentEmotion emotion WHERE emotion.reComment = reComment AND emotion.type = 'LIKE'), " +
            "(SELECT COUNT(emotion) FROM ReCommentEmotion emotion WHERE emotion.reComment = reComment AND emotion.type = 'DISLIKE'), " +
            "reComment.createdAt) " +
            "FROM ReComment reComment " +
            "LEFT JOIN ReCommentEmotion reCommentEmotion ON reCommentEmotion.reComment = reComment AND reCommentEmotion.memberId = :currentMemberId " +
            "WHERE reComment.comment = :comment " +
            "ORDER BY reComment.createdAt")
    Slice<ReCommentInfo> findSliceByCommentWhenLogin(@Param("comment") Comment comment, @Param("currentMemberId") Long currentMemberId, Pageable pageable);

    // 비로그인 시 감정은 EMOTIONLESS를 반환한다
    @Query("SELECT new com.naver.webtoon.comment.dto.response.ReCommentInfo(reComment.id, reComment.content, reComment.member.username, " +
            "'EMOTIONLESS', " +
            "(SELECT COUNT(emotion) FROM ReCommentEmotion emotion WHERE emotion.reComment = reComment AND emotion.type = 'LIKE'), " +
            "(SELECT COUNT(emotion) FROM ReCommentEmotion emotion WHERE emotion.reComment = reComment AND emotion.type = 'DISLIKE'), " +
            "reComment.createdAt) " +
            "FROM ReComment reComment " +
            "LEFT JOIN ReCommentEmotion reCommentEmotion ON reCommentEmotion.reComment = reComment " +
            "WHERE reComment.comment = :comment " +
            "ORDER BY reComment.createdAt")
    Slice<ReCommentInfo> findSliceByCommentWhenNonLogin(@Param("comment") Comment comment, Pageable pageable);
}
