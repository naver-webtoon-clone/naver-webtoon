package com.naver.webtoon.member.service;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.common.jwt.JwtUtil;
import com.naver.webtoon.member.dto.request.MemberLoginRequest;
import com.naver.webtoon.member.dto.request.MemberSignUpRequest;
import com.naver.webtoon.member.entity.Member;
import com.naver.webtoon.member.fixture.MemberFixture;
import com.naver.webtoon.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.naver.webtoon.member.fixture.MemberFixture.COOKIE_COUNT;
import static com.naver.webtoon.member.fixture.MemberFixture.PASSWORD;
import static com.naver.webtoon.member.fixture.MemberFixture.USERNAME;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtil jwtUtil;
    final String ENCRYPTED_PASSWORD = "encryptedPassword";

    @DisplayName("회원 가입 시 정상 케이스")
    @Test
    public void signUp() {
        MemberSignUpRequest request = new MemberSignUpRequest(USERNAME, PASSWORD);

        //stub
        when(memberRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(ENCRYPTED_PASSWORD);

        //when
        memberService.signUp(request);

        //then
        verify(memberRepository, times(1)).existsByUsername(request.getUsername());

        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(1)).save(memberArgumentCaptor.capture());

        Member savedMember = memberArgumentCaptor.getValue();
        assertThat(savedMember.getUsername()).isEqualTo(request.getUsername());
        assertThat(savedMember.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);

    }
    @DisplayName("회원 가입 시 중복된 이름이 있다면 예외를 반환한다.")
    @Test
    public void throwIfExistDuplicatedUsernameWhenSignUp() {
        MemberSignUpRequest request = new MemberSignUpRequest(USERNAME, PASSWORD);

        when(memberRepository.existsByUsername(request.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> memberService.signUp(request))
                .isInstanceOf(WebtoonException.class);
    }

    @DisplayName("로그인 시 정상 케이스.")
    @Test
    public void login() {
        MemberLoginRequest request = new MemberLoginRequest(USERNAME, PASSWORD);
        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);

        when(memberRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(request.getPassword(), member.getPassword())).thenReturn(true);
        when(jwtUtil.createAccessToken(request.getUsername())).thenReturn("token");

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        memberService.login(request, response);

        verify(response).addHeader(Mockito.eq("AccessToken"), Mockito.anyString());
    }

    @DisplayName("로그인 시 회원을 찾지 못하면 예외를 반환한다.")
    @Test
    public void throwIfCannotFoundMemberWhenLogin() {
        MemberLoginRequest request = new MemberLoginRequest(USERNAME, PASSWORD);

        Mockito.when(memberRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        assertThatThrownBy(() -> memberService.login(request,response))
                .isInstanceOf(WebtoonException.class);
    }

    @DisplayName("로그인 시 비밀번호가 다르면 예외를 반환한다.")
    @Test
    public void throwIfDifferentPasswordWhenLogin() {
        MemberLoginRequest request = new MemberLoginRequest(USERNAME, PASSWORD);
        Member member = MemberFixture.toMember(USERNAME, PASSWORD, COOKIE_COUNT);

        when(memberRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(request.getPassword(), member.getPassword())).thenReturn(false);

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        assertThatThrownBy(() -> memberService.login(request,response))
                .isInstanceOf(WebtoonException.class);
    }
}
