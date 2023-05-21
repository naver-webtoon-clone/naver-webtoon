package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
    @Query("SELECT wpd.webtoon FROM WebtoonPublishingDay wpd " +
            "JOIN wpd.publishingDay pd " +
            "WHERE pd.dayOfTheWeek = :dayOfTheWeek " +
            "AND (wpd.webtoon.serializedStatus = 'BREAK' " +
            "OR wpd.webtoon.serializedStatus = 'SERIALIZED')")
    List<Webtoon> findOnGoingWebtoonByDayOfTheWeek(@Param("dayOfTheWeek")DayOfTheWeek dayOfTheWeek);


    @Query("SELECT wpd.webtoon FROM WebtoonPublishingDay wpd " +
            "JOIN wpd.publishingDay pd " +
            "WHERE pd.dayOfTheWeek = :dayOfTheWeek " +
            "AND (wpd.webtoon.serializedStatus = 'BREAK' " +
            "OR wpd.webtoon.serializedStatus = 'SERIALIZED')" +
            "ORDER BY wpd.webtoon.updatedAt DESC")
    List<Webtoon> findOnGoingWebtoonByDayOfTheWeekOrderByLastedUpdate(@Param("dayOfTheWeek")DayOfTheWeek dayOfTheWeek);

    List<Webtoon> findAll();
}
