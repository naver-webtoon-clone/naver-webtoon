package com.naver.webtoon.comment.controller;

import com.naver.webtoon.comment.dto.request.ReCommentUpdateRequest;
import com.naver.webtoon.comment.dto.request.ReCommentWriteRequest;
import com.naver.webtoon.comment.service.ReCommentService;
import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class ReCommentController {

    private final ReCommentService reCommentService;

    @PostMapping("/comment/{commentId}/re-comment")
    public ResponseEntity<SuccessMessage<Void>> writeReComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @Valid @RequestBody ReCommentWriteRequest request) {
        reCommentService.writeReComment(userDetails.getMember(), commentId, request);
        return new ResponseEntity<>(new SuccessMessage<>("댓글답글작성성공",null), HttpStatus.CREATED);
    }

    @PutMapping("/re-comment/{reCommentId}")
    public ResponseEntity<SuccessMessage<Void>> updateReComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reCommentId, @Valid @RequestBody ReCommentUpdateRequest request) {
        reCommentService.updateReComment(userDetails.getMember(), reCommentId, request);
        return new ResponseEntity<>(new SuccessMessage<>("댓글답글수정성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/re-commnet/{reCommentId}")
    public ResponseEntity<SuccessMessage<Void>> deleteReComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reCommentId) {
        reCommentService.deleteReComment(userDetails.getMember(), reCommentId);
        return new ResponseEntity<>(new SuccessMessage<>("댓글답글삭제성공",null), HttpStatus.OK);
    }

    @PostMapping("/re-comment/{reCommentId}/like")
    public ResponseEntity<SuccessMessage<Void>> registerReCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reCommentId) {
        reCommentService.registerReCommentLike(userDetails.getMember(), reCommentId);
        return new ResponseEntity<>(new SuccessMessage<>("댓글답글좋아요등록성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/re-comment/{reCommentId}/like")
    public ResponseEntity<SuccessMessage<Void>> deleteReCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reCommentId) {
        reCommentService.deleteReCommentLike(userDetails.getMember(), reCommentId);
        return new ResponseEntity<>(new SuccessMessage<>("댓글답글좋아요삭제성공",null), HttpStatus.OK);
    }

    @PostMapping("/re-comment/{reCommentId}/dislike")
    public ResponseEntity<SuccessMessage<Void>> registerReCommentDislike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reCommentId) {
        reCommentService.registerReCommentDislike(userDetails.getMember(), reCommentId);
        return new ResponseEntity<>(new SuccessMessage<>("댓글답글싫어요등록성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/re-comment/{reCommentId}/dislike")
    public ResponseEntity<SuccessMessage<Void>> deleteReCommentDislike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reCommentId) {
        reCommentService.deleteReCommentDislike(userDetails.getMember(), reCommentId);
        return new ResponseEntity<>(new SuccessMessage<>("댓글답글싫어요삭제성공",null), HttpStatus.OK);
    }
}
