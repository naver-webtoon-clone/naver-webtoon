package com.naver.webtoon.comment.repository;

import com.naver.webtoon.comment.entity.Comment;
import com.naver.webtoon.comment.entity.CommentEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentEmotionRepository extends JpaRepository<CommentEmotion, Long>{

    boolean existsByMemberIdAndComment(Long memberId, Comment comment);
    Optional<CommentEmotion> findByMemberIdAndComment(Long memberId, Comment comment);
}
