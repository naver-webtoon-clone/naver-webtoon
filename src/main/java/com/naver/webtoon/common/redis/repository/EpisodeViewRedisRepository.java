package com.naver.webtoon.common.redis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EpisodeViewRedisRepository {

    private final RedisTemplate<String, Long> redisTemplate;
    private final static int VIEW = 1;
    private final static String KEY_NAME = "webtoonId: ";

    public void increaseWebtoonViewCountWith30DaysAfterExpired(Long webtoonId) {
        String key = KEY_NAME + webtoonId;
        redisTemplate.opsForValue().increment(key, VIEW);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after30Days = now.toLocalDate().plusDays(30).atStartOfDay();
        Duration duration = Duration.between(now, after30Days);

        redisTemplate.expire(key, duration);
    }
}
