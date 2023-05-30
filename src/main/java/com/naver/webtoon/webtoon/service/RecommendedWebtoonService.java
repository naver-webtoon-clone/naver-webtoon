package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.webtoon.repository.RecommendedWebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendedWebtoonService {

    private final RecommendedWebtoonRepository recommendedWebtoonRepository;

    @Transactional
    public void registerRecommendedWebtoon(Long webtoonId){
        /*
        webtoonId로 해당 웹툰 아이디의 정보를 갖고와서 recommendedWebtoon의 정보를 등록.
         */

    }

    @Transactional
    public void deleteRecommendedWebtoon(Long webtoonId){
        /*
        webtoonId로 해당 웹툰 아이디의 정보를 갖고와서 recommendedWebtoon의 정보를 삭제.
         */
    }
}
