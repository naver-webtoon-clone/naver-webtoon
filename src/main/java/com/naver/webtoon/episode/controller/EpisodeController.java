package com.naver.webtoon.episode.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.common.security.UserDetailsImpl;
import com.naver.webtoon.episode.dto.request.EpisodeRegisterRequest;
import com.naver.webtoon.episode.dto.request.EpisodeUpdateRequest;
import com.naver.webtoon.episode.dto.response.EpisodeRetrieveResponse;
import com.naver.webtoon.episode.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/episode/{episodeId}")
    public ResponseEntity<SuccessMessage<Void>> updateEpisode(@PathVariable Long episodeId, @Valid @RequestBody EpisodeUpdateRequest request) {
        episodeService.updateEpisode(episodeId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드수정성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/episode/{episodeId}")
    public ResponseEntity<SuccessMessage<Void>> deleteEpisode(@PathVariable Long episodeId) {
        episodeService.deleteEpisode(episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드삭제성공",null), HttpStatus.OK);
    }

    @GetMapping("/episode/{episodeId}/public")
    public ResponseEntity<SuccessMessage<EpisodeRetrieveResponse>> retrievePublicEpisode(@PathVariable Long episodeId) {
        EpisodeRetrieveResponse response = episodeService.retrievePublicEpisode(episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("공개에피소드조회성공", response), HttpStatus.OK);
    }

    @GetMapping("/episode/{episodeId}/private")
    public ResponseEntity<SuccessMessage<EpisodeRetrieveResponse>> retrievePrivateEpisode(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long episodeId) {
        EpisodeRetrieveResponse response = episodeService.retrievePrivateEpisode(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("비공개에피소드조회성공", response), HttpStatus.OK);
    }
}
