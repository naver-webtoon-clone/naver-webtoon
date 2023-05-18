package com.naver.webtoon.member.contorller;

import com.naver.webtoon.common.response.SuccessMessage;
import com.naver.webtoon.member.dto.request.MemberLoginRequest;
import com.naver.webtoon.member.dto.request.MemberSignUpRequest;
import com.naver.webtoon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<SuccessMessage<Void>> signUp(@RequestBody MemberSignUpRequest request) {
        memberService.signUp(request);
        return new ResponseEntity<>(new SuccessMessage<>("회원가입성공",null), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<SuccessMessage<Void>> login(@RequestBody MemberLoginRequest request, HttpServletResponse response){

        memberService.login(request, response);
        return new ResponseEntity<>(new SuccessMessage<>("로그인성공",null), HttpStatus.OK);
    }
}
