package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.webtoon.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    Optional<HashTag> findByName(String hashTagName);
}
