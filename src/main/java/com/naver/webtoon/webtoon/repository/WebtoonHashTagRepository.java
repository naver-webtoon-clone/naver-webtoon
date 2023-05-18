package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.WebtoonHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonHashTagRepository extends JpaRepository<WebtoonHashTag, Long> {

    void deleteByWebtoonId(Long webtoonId);
}
