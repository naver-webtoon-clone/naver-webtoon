package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.WebtoonPublishingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonPublishingDayRepository extends JpaRepository<WebtoonPublishingDay, Long> {

    void deleteByWebtoonId(Long webtoonId);
}
