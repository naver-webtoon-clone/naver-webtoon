package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.webtoon.entity.RecommendedWebtoon;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.repository.RecommendedWebtoonRepository;
import com.naver.webtoon.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_WEBTOON;

@Service
@RequiredArgsConstructor
public class RecommendedWebtoonService {

    private final RecommendedWebtoonRepository recommendedWebtoonRepository;
    private final WebtoonRepository webtoonRepository;

    //TODO: 이미 등록 되있는 웹툰의 경우 exception 수정 필요.
    @Transactional
    public void registerRecommendedWebtoon(Long webtoonId){
        Webtoon webtoon = findWebtoon(webtoonId);
        RecommendedWebtoon recommendedWebtoon = RecommendedWebtoon.createRecommendedWebtoon(webtoon);
        recommendedWebtoonRepository.save(recommendedWebtoon);
    }

    //TODO: 등록 되있지 않은 웹툰의 경우 exception 수정 필요.
    @Transactional
    public void deleteRecommendedWebtoon(Long webtoonId){
        Webtoon webtoon = findWebtoon(webtoonId);
        recommendedWebtoonRepository.deleteByWebtoonId(webtoon.getId());
    }

    public Webtoon findWebtoon(Long webtoonId){
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(()->
                new WebtoonException(NOT_FOUND_WEBTOON));
        return webtoon;
    }
}
