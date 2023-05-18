package com.naver.webtoon.webtoon.controller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.webtoon.dto.request.AuthorRegisterRequest;
import com.naver.webtoon.webtoon.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/author")
    public ResponseEntity<SuccessMessage<Void>> registerAuthor(@RequestBody AuthorRegisterRequest request) {
        authorService.registerAuthor(request);
        return new ResponseEntity<>(new SuccessMessage<>("작가등록성공",null), HttpStatus.CREATED);
    }
}
