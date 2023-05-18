package com.naver.webtoon.common.time;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtils {

    public static boolean validateUpdatedToday(LocalDateTime updatedAt) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(updatedAt, currentTime);
        long hoursDifference = duration.toHours();
        return hoursDifference < 24;
    }

    public static boolean validateNewWebtoon(LocalDateTime updatedAt) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(updatedAt, currentTime);
        long hoursDifference = duration.toDays();
        return hoursDifference < 30;
    }
}
