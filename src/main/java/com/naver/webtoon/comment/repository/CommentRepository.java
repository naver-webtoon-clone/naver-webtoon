package com.naver.webtoon.comment.repository;

import com.naver.webtoon.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
