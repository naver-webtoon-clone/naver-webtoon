package com.naver.webtoon.member.dto.request;

import com.naver.webtoon.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignUpRequest {

    private String username;
    private String password;

    public Member toMember(String encryptedPassword){
        return Member.builder()
                .username(username)
                .password(encryptedPassword)
                .cookieCount(0)
                .build();
    }

    public MemberSignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
