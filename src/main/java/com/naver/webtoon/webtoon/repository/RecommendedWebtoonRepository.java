package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.RecommendedWebtoon;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendedWebtoonRepository extends JpaRepository<RecommendedWebtoon, Long> {
    void deleteByWebtoonId(Long webtoonId);

    @Query("SELECT wt FROM Webtoon wt " +
            "JOIN WebtoonPublishingDay wpd ON wt.id = wpd.webtoon.id " +
            "JOIN RecommendedWebtoon rw ON wt.id = rw.webtoon.id " +
            "JOIN wpd.publishingDay pd " +
            "WHERE pd.dayOfTheWeek = :dayOfTheWeek " +
            "AND (wt.serializedStatus = 'BREAK' OR wt.serializedStatus = 'SERIALIZED') " +
            "AND wt.id IN (SELECT rw.webtoon.id FROM RecommendedWebtoon rw)")
    List<Webtoon> findRecommendedWebtoonByDayOfTheWeek(@Param("dayOfTheWeek") DayOfTheWeek dayOfTheWeek);

    boolean existsByWebtoonId(Long webtoonId);
}
