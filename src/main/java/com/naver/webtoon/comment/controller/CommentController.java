package com.naver.webtoon.comment.controller;

import com.naver.webtoon.comment.dto.request.CommentUpdateRequest;
import com.naver.webtoon.comment.dto.request.CommentWriteRequest;
import com.naver.webtoon.comment.dto.response.BestCommentInfoListResponse;
import com.naver.webtoon.comment.dto.response.CommentInfoSliceResponse;
import com.naver.webtoon.comment.dto.response.ReCommentInfoSliceResponse;
import com.naver.webtoon.comment.service.CommentService;
import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.common.security.UserDetailsImpl;
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
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/episode/{episodeId}/comment")
    public ResponseEntity<SuccessMessage<Void>> writeComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long episodeId, @Valid @RequestBody CommentWriteRequest request) {
        commentService.writeComment(userDetails.getMember(), episodeId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글작성성공",null), HttpStatus.CREATED);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<SuccessMessage<Void>> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest request) {
        commentService.updateComment(userDetails.getMember(), commentId, request);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글수정성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/commnet/{commentId}")
    public ResponseEntity<SuccessMessage<Void>> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        commentService.deleteComment(userDetails.getMember(), commentId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글삭제성공",null), HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<SuccessMessage<Void>> registerCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        commentService.registerCommentLike(userDetails.getMember(), commentId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글좋아요등록성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}/like")
    public ResponseEntity<SuccessMessage<Void>> deleteCommentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        commentService.deleteCommentLike(userDetails.getMember(), commentId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글좋아요삭제성공",null), HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}/dislike")
    public ResponseEntity<SuccessMessage<Void>> registerCommentDislike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        commentService.registerCommentDislike(userDetails.getMember(), commentId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글싫어요등록성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}/dislike")
    public ResponseEntity<SuccessMessage<Void>> deleteCommentDislike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        commentService.deleteCommentDislike(userDetails.getMember(), commentId);
        return new ResponseEntity<>(new SuccessMessage<>("에피소드댓글싫어요삭제성공",null), HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}/re-comment/{page}/login")
    public ResponseEntity<SuccessMessage<ReCommentInfoSliceResponse>> retrieveReCommentWhenLogin(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @PathVariable int page) {
        ReCommentInfoSliceResponse response = commentService.retrieveReCommentWhenLogin(userDetails.getMember(), commentId, page);
        return new ResponseEntity<>(new SuccessMessage<>("답글조회성공", response), HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}/re-comment/{page}/non-login")
    public ResponseEntity<SuccessMessage<ReCommentInfoSliceResponse>> retrieveReCommentWhenNonLogin(@PathVariable Long commentId, @PathVariable int page) {
        ReCommentInfoSliceResponse response = commentService.retrieveReCommentWhenNonLogin(commentId, page);
        return new ResponseEntity<>(new SuccessMessage<>("답글조회성공", response), HttpStatus.OK);
    }

    @GetMapping("/episode/{episodeId}/comment/best/login")
    public ResponseEntity<SuccessMessage<BestCommentInfoListResponse>> retrieveBestComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long episodeId) {
        BestCommentInfoListResponse response = commentService.retrieveBestCommentWhenLogin(userDetails.getMember(), episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("베스트댓글조회성공", response), HttpStatus.OK);
    }

    @GetMapping("/episode/{episodeId}/comment/best/non-login")
    public ResponseEntity<SuccessMessage<BestCommentInfoListResponse>> retrieveBestCommentWhenNonLogin(@PathVariable Long episodeId) {
        BestCommentInfoListResponse response = commentService.retrieveBestCommentWhenNonLogin(episodeId);
        return new ResponseEntity<>(new SuccessMessage<>("베스트댓글조회성공", response), HttpStatus.OK);
    }

    @GetMapping("/episode/{episodeId}/comment/{page}/all/login")
    public ResponseEntity<SuccessMessage<CommentInfoSliceResponse>> retrieveAllCommentWhenLogin(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long episodeId, @PathVariable int page) {
        CommentInfoSliceResponse response = commentService.retrieveAllCommentWhenLogin(userDetails.getMember(), episodeId, page);
        return new ResponseEntity<>(new SuccessMessage<>("댓글전체조회성공", response), HttpStatus.OK);
    }

    @GetMapping("/episode/{episodeId}/comment/{page}/all/non-login")
    public ResponseEntity<SuccessMessage<CommentInfoSliceResponse>> retrieveAllCommentWhenNonLogin(@PathVariable Long episodeId, @PathVariable int page) {
        CommentInfoSliceResponse response = commentService.retrieveAllCommentWhenNonLogin(episodeId, page);
        return new ResponseEntity<>(new SuccessMessage<>("댓글전체조회성공", response), HttpStatus.OK);
    }
}
