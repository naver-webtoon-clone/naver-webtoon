package com.naver.webtoon.comment.repository;

import com.naver.webtoon.comment.entity.ReComment;
import com.naver.webtoon.comment.entity.ReCommentEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReCommentEmotionRepository extends JpaRepository<ReCommentEmotion, Long> {

    Optional<ReCommentEmotion> findByMemberIdAndReComment(Long memberId, ReComment reComment);
    boolean existsByMemberIdAndReComment(Long memberId, ReComment reComment);
}
