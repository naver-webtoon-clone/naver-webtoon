package com.naver.webtoon.webtoon.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.webtoon.dto.request.WebtoonRegisterRequest;
import com.naver.webtoon.webtoon.dto.response.RecommendedWebtoonInfoResponse;
import com.naver.webtoon.webtoon.dto.response.WebtoonInfoListResponse;
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

    @PostMapping("/webtoon/{webtoonId}/recommended")
    public ResponseEntity<SuccessMessage<Void>> registerRecommendedWebtoon(@PathVariable Long webtoonId) {
        recommendedWebtoonService.registerRecommendedWebtoon(webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("요일별추천웹툰등록성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/webtoon/{recommendedWebtoonId}/recommended")
    public ResponseEntity<SuccessMessage<Void>> deleteRecommendedWebtoon(@PathVariable Long recommendedWebtoonId) {
        recommendedWebtoonService.deleteRecommendedWebtoon(recommendedWebtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("요일별추천웹툰삭제성공",null), HttpStatus.OK);
    }

    @GetMapping("/webtoon/{publishingDay}/recommend")
    public ResponseEntity<SuccessMessage<RecommendedWebtoonInfoResponse>> getRecommendedWebtoonsByDayOfWeek(@PathVariable String publishingDay) {
        RecommendedWebtoonInfoResponse response = recommendedWebtoonService.getRecommendedWebtoonsByDayOfWeek(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별인기순웹툰조회성공",response), HttpStatus.OK);
    }
}
