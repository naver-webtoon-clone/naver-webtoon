package com.naver.webtoon.episode.service;

import com.naver.webtoon.common.exception.WebtoonException;

import com.naver.webtoon.common.redis.repository.EpisodeViewRedisRepository;
import com.naver.webtoon.episode.dto.request.EpisodeRegisterRequest;
import com.naver.webtoon.episode.dto.request.EpisodeUpdateRequest;
import com.naver.webtoon.episode.dto.response.EpisodeRetrieveResponse;
import com.naver.webtoon.episode.entity.Episode;
import com.naver.webtoon.episode.repository.EpisodeRepository;
import com.naver.webtoon.member.entity.Member;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.naver.webtoon.common.exception.ErrorCode.FREE_EPISODE_MUST_HAVE_FREE_RELEASE_DATE_IS_NULL;
import static com.naver.webtoon.common.exception.ErrorCode.FREE_RELEASE_DATE_MUST_BE_AFTER_THAN_CURRENT_DATE;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_EPISODE;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_WEBTOON;
import static com.naver.webtoon.common.exception.ErrorCode.PRIVATE_EPISODE_INACCESSIBILITY;
import static com.naver.webtoon.common.exception.ErrorCode.PRIVATE_EPISODE_MUST_BE_PAID;
import static com.naver.webtoon.common.exception.ErrorCode.PUBLIC_EPISODE_INACCESSIBILITY;
import static com.naver.webtoon.common.exception.ErrorCode.PUBLIC_EPISODE_MUST_BE_FREE;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final WebtoonRepository webtoonRepository;
    private final EpisodeViewRedisRepository episodeViewRedisRepository;

    private final static int AMOUNT_OF_PUBLIC_EPISODE_COOKIE = 0;

    @Transactional
    public void registerEpisode(Long webtoonId, EpisodeRegisterRequest request) {
        boolean isPublic = request.getIsPublic();
        int neededCookieAmount = request.getNeededCookieAmount();

        LocalDate freeReleaseDate = request.getFreeReleaseDate();

        throwIfFreeForPrivateEpisode(isPublic, neededCookieAmount);
        throwIfPaidForPublicEpisode(isPublic, neededCookieAmount);

        throwIfFreeReleaseDateEnteredForPublicEpisode(isPublic, freeReleaseDate);
        throwIfFreeReleaseDateIsCurrentDateOrLess(freeReleaseDate);

        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));

        Episode episode = request.toEpisode(webtoon);

        episodeRepository.save(episode);
    }


    @Transactional
    public void updateEpisode(Long episodeId, EpisodeUpdateRequest request) {
        String title = request.getTitle();
        String content = request.getContent();
        String postscript = request.getPostscript();
        boolean isPublic = request.getIsPublic();
        int neededCookieAmount = request.getNeededCookieAmount();
        LocalDate freeReleaseDate = request.getFreeReleaseDate();

        throwIfFreeForPrivateEpisode(isPublic, neededCookieAmount);
        throwIfPaidForPublicEpisode(isPublic, neededCookieAmount);
        throwIfFreeReleaseDateEnteredForPublicEpisode(isPublic, freeReleaseDate);
        throwIfFreeReleaseDateIsCurrentDateOrLess(freeReleaseDate);

        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));

        episode.update(title, content, postscript, isPublic, neededCookieAmount, freeReleaseDate);
    }

    @Transactional
    public void deleteEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));

        episodeRepository.delete(episode);
    }

    @Transactional
    public EpisodeRetrieveResponse retrievePublicEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));
        Webtoon webtoon = episode.getWebtoon();

        boolean isPublic = episode.getIsPublic();
        throwIfPrivateEpisode(isPublic);

        episodeRepository.increaseViewCountAtomically(episode.getId());
        episodeViewRedisRepository.increaseWebtoonViewCountWith30DaysAfterExpired(webtoon.getId());

        return EpisodeRetrieveResponse.toResponse(episode);
    }

    @Transactional
    public EpisodeRetrieveResponse retrievePrivateEpisode(Member currentMember, Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_EPISODE));
        Webtoon webtoon = episode.getWebtoon();

        boolean isPublic = episode.getIsPublic();
        throwIfPublicEpisode(isPublic);
        /*
        TODO:
         쿠키 사용 기록 엔티티 생성 후 구현 할 것
         현재 멤버와 에피소드를 포함하는 쿠키 사용 기록이 테이블에 없다면
         예외를 반환한다.
         메서드 명 -> throwIfMemberDontPurchaseEpisode();
        */

        episodeRepository.increaseViewCountAtomically(episode.getId());
        episodeViewRedisRepository.increaseWebtoonViewCountWith30DaysAfterExpired(webtoon.getId());

        return EpisodeRetrieveResponse.toResponse(episode);
    }

    private void throwIfFreeForPrivateEpisode(boolean isPublic, int neededCookieAmount) {
        boolean isFree = (neededCookieAmount == AMOUNT_OF_PUBLIC_EPISODE_COOKIE);
        if (!isPublic && isFree) {
            throw new WebtoonException(PRIVATE_EPISODE_MUST_BE_PAID);
        }
    }

    private void throwIfPaidForPublicEpisode(boolean isPublic, int neededCookieAmount) {
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

    private void throwIfPrivateEpisode(boolean isPublic) {
        boolean isPrivate = !isPublic;
        if (isPrivate) {
            throw new WebtoonException(PRIVATE_EPISODE_INACCESSIBILITY);
        }
    }

    private void throwIfPublicEpisode(boolean isPublic) {
        if (isPublic) {
            throw new WebtoonException(PUBLIC_EPISODE_INACCESSIBILITY);
        }
    }
}
