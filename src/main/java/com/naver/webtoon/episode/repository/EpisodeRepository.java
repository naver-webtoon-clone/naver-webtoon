package com.naver.webtoon.episode.repository;

import com.naver.webtoon.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    @Modifying
    @Query("UPDATE Episode e SET e.views = e.views + 1 WHERE e.id = :episodeId")
    void increaseViewCountAtomically(@Param("episodeId") Long episodeId);
}
