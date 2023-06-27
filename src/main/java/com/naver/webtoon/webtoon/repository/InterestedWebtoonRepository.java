package com.naver.webtoon.webtoon.repository;

import com.naver.webtoon.member.entity.Member;
import com.naver.webtoon.webtoon.entity.InterestedWebtoon;
import com.naver.webtoon.webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestedWebtoonRepository extends JpaRepository<InterestedWebtoon, Long> {

    boolean existsByMemberAndWebtoon(Member member, Webtoon webtoon);
}
