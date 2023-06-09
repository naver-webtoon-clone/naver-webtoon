package com.naver.webtoon.webtoon.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.webtoon.dto.request.WebtoonRegisterRequest;
import com.naver.webtoon.webtoon.dto.request.WebtoonUpdateRequest;
import com.naver.webtoon.webtoon.dto.response.RealTimePopularWebtoonInfoResponse;
import com.naver.webtoon.webtoon.dto.response.WebtoonInfoListResponse;
import com.naver.webtoon.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WebtoonController {

    private final WebtoonService webtoonService;

    @PostMapping("/webtoon")
    public ResponseEntity<SuccessMessage<Void>> registerWebtoon(@RequestBody WebtoonRegisterRequest request) {
        webtoonService.registerWebtoon(request);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰등록성공",null), HttpStatus.CREATED);
    }

    @PutMapping("/webtoon/{webtoonId}")
    public ResponseEntity<SuccessMessage<Void>> updateWebtoon(@PathVariable Long webtoonId, @RequestBody WebtoonUpdateRequest request) {
        webtoonService.updateWebtoon(webtoonId, request);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰수정성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/webtoon/{webtoonId}")
    public ResponseEntity<SuccessMessage<Void>> deleteWebtoon(@PathVariable Long webtoonId) {
        webtoonService.deleteWebtoon(webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("웹툰삭제성공",null), HttpStatus.OK);
    }

    //TODO: 인기순 -> api를 실행하는 기준으로 30일안에 조회수가 많은 순서대로 조회
    @GetMapping("/webtoon/{publishingDay}/popular")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getPopularWebtoonsByDayOfWeekAndWithin30Days(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getPopularWebtoonsByDayOfWeekAndWithin30Days(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별인기순웹툰조회성공",response), HttpStatus.OK);
    }

    @GetMapping("/webtoon/{publishingDay}/latest-update")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getlastestUpdateWebtoonsByDayOfWeek(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getlastestUpdateWebtoonsByDayOfWeek(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별업데이트순웹툰조회",response), HttpStatus.OK);
    }

    @GetMapping("/webtoon/{publishingDay}/total-views")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getTotalViewsWebtoonsByDayOfWeek(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getTotalViewsWebtoonsByDayOfWeek(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별조회순웹툰조회",response), HttpStatus.OK);
    }

    //TODO: 에피소드의 별점 테이블 생성 후 기능 수정 필요.
    @GetMapping("/webtoon/{publishingDay}/highest-stars")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getHigestStarsWebtoonsByDayOfWeek(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getHigestStarsWebtoonsByDayOfWeek(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별별점순웹툰조회",response), HttpStatus.OK);
    }

    @GetMapping("/webtoon/popular")
    public ResponseEntity<SuccessMessage<RealTimePopularWebtoonInfoResponse>> getRealTimePopularWebtoons() {
        RealTimePopularWebtoonInfoResponse response = webtoonService.getRealTimePopularWebtoons();
        return new ResponseEntity<>(new SuccessMessage<>("실시간인기웹툰조회성공",response), HttpStatus.OK);
    }
}
