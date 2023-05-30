package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.RecommendedWebtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedWebtoonRepository extends JpaRepository<RecommendedWebtoon, Long> {
    void deleteByWebtoonId(Long webtoonId);
}
