package com.naver.webtoon.webtoon.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.webtoon.dto.request.WebtoonRegisterRequest;
import com.naver.webtoon.webtoon.dto.request.WebtoonUpdateRequest;
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

    @GetMapping("/webtoon/{publishingDay}/popular")
    public ResponseEntity<SuccessMessage<WebtoonInfoListResponse>> getPopularWebtoonsByDayOfWeek(@PathVariable String publishingDay) {
        WebtoonInfoListResponse response = webtoonService.getPopularWebtoonsByDayOfWeek(publishingDay);
        return new ResponseEntity<>(new SuccessMessage<>("요일별인기순웹툰조회성공",response), HttpStatus.OK);
    }
}
