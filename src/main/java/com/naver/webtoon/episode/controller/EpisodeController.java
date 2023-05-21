package com.naver.webtoon.episode.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.episode.dto.EpisodeRegisterRequest;
import com.naver.webtoon.episode.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping("/webtoon/{webtoonId}")
    public ResponseEntity<SuccessMessage<Void>> registerEpisode(@PathVariable Long webtoonId, @Valid @RequestBody EpisodeRegisterRequest request) {
        episodeService.registerEpisode(webtoonId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드등록성공",null), HttpStatus.CREATED);
    }

}
