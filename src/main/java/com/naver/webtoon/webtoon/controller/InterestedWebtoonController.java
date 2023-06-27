package com.naver.webtoon.webtoon.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.common.security.UserDetailsImpl;
import com.naver.webtoon.webtoon.service.InterestedWebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InterestedWebtoonController {

    private final InterestedWebtoonService interestedWebtoonService;

    @PostMapping("/webtoon/{webtoonId}/interested")
    public ResponseEntity<SuccessMessage<Void>> registerInterestedWebtoon(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long webtoonId) {
        interestedWebtoonService.registerInterestedWebtoon(userDetails.getMember(), webtoonId);
        return new ResponseEntity<>(new SuccessMessage<>("관심웹툰등록성공",null), HttpStatus.CREATED);
    }
}
