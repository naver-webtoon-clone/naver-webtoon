package com.naver.webtoon.member.service;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.common.jwt.JwtUtil;
import com.naver.webtoon.member.dto.request.MemberLoginRequest;
import com.naver.webtoon.member.dto.request.MemberSignUpRequest;
import com.naver.webtoon.member.entity.Member;
import com.naver.webtoon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static com.naver.webtoon.common.exception.ErrorCode.ALREADY_EXIST_USERNAME;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_VALID_PASSWORD;
import static com.naver.webtoon.common.jwt.JwtUtil.AUTHORIZATION_ACCESS;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(MemberSignUpRequest request) {

        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new WebtoonException(ALREADY_EXIST_USERNAME);
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Member member = request.toMember(encryptedPassword);

        memberRepository.save(member);
    }

    @Transactional
    public void login(MemberLoginRequest request, HttpServletResponse response) {

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_MEMBER));

        validatePasswordMatch(request.getPassword(), member.getPassword());

        issueTokens(response, member.getUsername());
    }

    public void issueTokens(HttpServletResponse response, String username) {

        String accessToken = jwtUtil.createAccessToken(username);
        response.addHeader(AUTHORIZATION_ACCESS, accessToken);
    }

    public void validatePasswordMatch(String encryptedPassword, String inputPassword) {

        if (passwordEncoder.matches(encryptedPassword, inputPassword)) {
            return;
        }
        throw new WebtoonException(NOT_VALID_PASSWORD);
    }
}
