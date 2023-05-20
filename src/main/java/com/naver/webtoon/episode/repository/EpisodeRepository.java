package com.naver.webtoon.episode.repository;

import com.naver.webtoon.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
