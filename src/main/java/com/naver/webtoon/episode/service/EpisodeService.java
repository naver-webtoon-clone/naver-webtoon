package com.naver.webtoon.episode.service;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.episode.dto.EpisodeRegisterRequest;
import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.episode.repository.EpisodeRepository;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.naver.webtoon.common.exception.ErrorCode.FREE_EPISODE_MUST_HAVE_FREE_RELEASE_DATE_IS_NULL;
import static com.naver.webtoon.common.exception.ErrorCode.FREE_RELEASE_DATE_MUST_BE_AFTER_THAN_CURRENT_DATE;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_WEBTOON;
import static com.naver.webtoon.common.exception.ErrorCode.PRIVATE_EPISODE_MUST_BE_PAID;
import static com.naver.webtoon.common.exception.ErrorCode.PUBLIC_EPISODE_MUST_BE_FREE;


@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final WebtoonRepository webtoonRepository;
    private final static int AMOUNT_OF_PUBLIC_EPISODE_COOKIE = 0;

    @Transactional
    public void registerEpisode(Long webtoonId, EpisodeRegisterRequest request) {
        boolean isPublic = request.getIsPublic();
        int neededCookieAmount = request.getNeededCookieAmount();
        throwIfFreeForPrivateEpisode(isPublic, neededCookieAmount);
        throwIfPaidForFreeEpisode(isPublic, neededCookieAmount);

        LocalDate freeReleaseDate = request.getFreeReleaseDate();
        throwIfFreeReleaseDateEnteredForPublicEpisode(isPublic, freeReleaseDate);
        throwIfFreeReleaseDateIsCurrentDateOrLess(freeReleaseDate);

        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));

        Episode episode = request.toEpisode(webtoon);

        episodeRepository.save(episode);
    }

    private void throwIfFreeForPrivateEpisode(boolean isPublic, int neededCookieAmount) {
        boolean isFree = (neededCookieAmount == AMOUNT_OF_PUBLIC_EPISODE_COOKIE);
        if (!isPublic && isFree) {
            throw new WebtoonException(PRIVATE_EPISODE_MUST_BE_PAID);
        }
    }

    private void throwIfPaidForFreeEpisode(boolean isPublic, int neededCookieAmount) {
        boolean isFree = (neededCookieAmount == AMOUNT_OF_PUBLIC_EPISODE_COOKIE);
        if (isPublic && !isFree) {
            throw new WebtoonException(PUBLIC_EPISODE_MUST_BE_FREE);
        }
    }

    private void throwIfFreeReleaseDateEnteredForPublicEpisode(boolean isPublic, LocalDate freeReleaseDate) {
        boolean isFreeReleaseDateEntered =  (freeReleaseDate != null);
        if(isPublic && isFreeReleaseDateEntered) {
            throw new WebtoonException(FREE_EPISODE_MUST_HAVE_FREE_RELEASE_DATE_IS_NULL);
        }
    }

    private void throwIfFreeReleaseDateIsCurrentDateOrLess(LocalDate freeReleaseDate) {
        LocalDate currentDate = LocalDate.now();
        boolean isFreeReleaseDateEntered =  (freeReleaseDate != null);
        if (isFreeReleaseDateEntered && (freeReleaseDate.isBefore(currentDate) || freeReleaseDate.isEqual(currentDate))) {
            throw new WebtoonException(FREE_RELEASE_DATE_MUST_BE_AFTER_THAN_CURRENT_DATE);
        }
    }
}
