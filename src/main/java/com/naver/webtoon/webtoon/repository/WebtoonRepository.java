package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

}
