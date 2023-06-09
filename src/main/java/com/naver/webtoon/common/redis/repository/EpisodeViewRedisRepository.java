package com.naver.webtoon.common.redis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpisodeViewRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final static long VIEW = 1;
    private final static String KEY_NAME = "webtoonId: ";

    public void increaseWebtoonViewCountWith30DaysAfterExpired(Long webtoonId) {
        String key = KEY_NAME + webtoonId;
        long currentTime = System.currentTimeMillis();

        redisTemplate.opsForZSet().incrementScore(key, VIEW, currentTime);

        //TODO: 아래 로직은 스프링 스케쥴러를 이용해 모든 키값에 대하여 자정에 실행되도록 변경
        long thirtyDaysAgo = currentTime - (30L * 24 * 60 * 60 * 1000);
        
        redisTemplate.opsForZSet().removeRangeByScore(key, 0, thirtyDaysAgo);
    }
}
