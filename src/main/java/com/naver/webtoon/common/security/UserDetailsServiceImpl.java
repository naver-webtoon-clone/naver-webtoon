package com.naver.webtoon.common.security;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.member.entity.Member;
import com.naver.webtoon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_MEMBER)
        );
        return new UserDetailsImpl(member, member.getUsername());
    }
}
