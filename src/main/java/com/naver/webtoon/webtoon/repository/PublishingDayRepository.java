package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.PublishingDay;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublishingDayRepository extends JpaRepository<PublishingDay, Long> {

    Optional<PublishingDay> findByDayOfTheWeek(DayOfTheWeek dayOfTheWeek);
}
