package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.dto.response.CompletedWebtoonInfoSliceResponse;
import com.naver.webtoon.webtoon.dto.response.CompletedWebtoonsByPopularityInfo;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @Query("SELECT new com.naver.webtoon.webtoon.dto.response.CompletedWebtoonsByPopularityInfo(wb.id, wb.title, wb.author.name, wb.thumbnail, " +
            "CASE WHEN wb.serializedStatus = 'BREAK' THEN 'BREAK' WHEN wb.serializedStatus = 'SERIALIZED' THEN 'SERIALIZED' ELSE false END) " +
            "FROM Webtoon wb " +
            "WHERE wb.serializedStatus = 'BREAK'")
    Slice<CompletedWebtoonsByPopularityInfo> findCompletedWebtoonsByPopularityOrderByPopularityDesc(Pageable pageRequest);
}
