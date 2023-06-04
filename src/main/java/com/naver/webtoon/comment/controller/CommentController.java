package com.naver.webtoon.comment.controller;

import com.naver.webtoon.comment.dto.request.CommentUpdateRequest;
import com.naver.webtoon.comment.dto.request.CommentWriteRequest;
import com.naver.webtoon.comment.service.CommentService;
import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/episode/{episodeId}/comment")
    public ResponseEntity<SuccessMessage<Void>> registerEpisode(UserDetailsImpl userDetails, @PathVariable Long episodeId, @Valid @RequestBody CommentWriteRequest request) {
        commentService.writeComment(userDetails.getMember(), episodeId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글작성성공",null), HttpStatus.CREATED);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<SuccessMessage<Void>> updateEpisode(UserDetailsImpl userDetails, @PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest request) {
        commentService.updateComment(userDetails.getMember(), commentId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글수정성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/commnet/{commentId}")
    public ResponseEntity<SuccessMessage<Void>> deleteEpisode(UserDetailsImpl userDetails, @PathVariable Long commentId) {
        commentService.deleteComment(userDetails.getMember(), commentId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글삭제성공",null), HttpStatus.OK);
    }
}
