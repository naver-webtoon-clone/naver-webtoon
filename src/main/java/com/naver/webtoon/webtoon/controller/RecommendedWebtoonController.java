package com.naver.webtoon.webtoon.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.webtoon.dto.request.WebtoonRegisterRequest;
import com.naver.webtoon.webtoon.service.RecommendedWebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendedWebtoonController {

    private final RecommendedWebtoonService recommendedWebtoonService;

    @PostMapping("/webtoon/recommended/{id}")
    public ResponseEntity<SuccessMessage<Void>> registerRecommendedWebtoon(@PathVariable Long webtoonId) {
        recommendedWebtoonService.registerRecommendedWebtoon(webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("요일별추천웹툰등록성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/webtoon/recommended/{id}")
    public ResponseEntity<SuccessMessage<Void>> deleteRecommendedWebtoon(@PathVariable Long webtoonId) {
        recommendedWebtoonService.deleteRecommendedWebtoon(webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("요일별추천웹툰삭제성공",null), HttpStatus.OK);
    }
}
