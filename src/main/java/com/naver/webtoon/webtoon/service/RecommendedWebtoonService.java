package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.webtoon.dto.response.RecommendedWebtoonInfoResponse;
import com.naver.webtoon.webtoon.dto.response.WebtoonInfoListResponse;
import com.naver.webtoon.webtoon.entity.RecommendedWebtoon;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import com.naver.webtoon.webtoon.repository.RecommendedWebtoonRepository;
import com.naver.webtoon.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.naver.webtoon.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class RecommendedWebtoonService {

    private final RecommendedWebtoonRepository recommendedWebtoonRepository;
    private final WebtoonRepository webtoonRepository;

    @Transactional
    public void registerRecommendedWebtoon(Long webtoonId) {
        if (recommendedWebtoonRepository.existsByWebtoonId(webtoonId)) {
            throw new WebtoonException(ALREADY_EXIST_WEBTOON);
        }
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(()->
                new WebtoonException(NOT_FOUND_WEBTOON));
        RecommendedWebtoon recommendedWebtoon = RecommendedWebtoon.createRecommendedWebtoon(webtoon);
        recommendedWebtoonRepository.save(recommendedWebtoon);
    }

    @Transactional
    public void deleteRecommendedWebtoon(Long recommendedWebtoonId) {
        RecommendedWebtoon recommendedWebtoon = recommendedWebtoonRepository.findById(recommendedWebtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_RECOMMENDED_WEBTOON));

        recommendedWebtoonRepository.delete(recommendedWebtoon);
    }

    //TODO:
    @Transactional
    public RecommendedWebtoonInfoResponse getRecommendedWebtoonsByDayOfWeek(String publishingDay) {
        DayOfTheWeek dayOfTheWeek = DayOfTheWeek.toEnum(publishingDay);
        List<Webtoon> webtoons = recommendedWebtoonRepository.findRecommendedWebtoonByDayOfTheWeek(dayOfTheWeek);
        return RecommendedWebtoonInfoResponse.toResponse(webtoons);
    }
}
