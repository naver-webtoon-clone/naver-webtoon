package com.naver.webtoon.member.fixture;

import com.naver.webtoon.member.entity.Member;

public class MemberFixture {

    public static final String USERNAME = "testUsername";
    public static final String PASSWORD = "testPassword";
    public static final Integer COOKIE_COUNT = 0;

    public static Member toMember(String username, String password, Integer cookieCount) {
        return Member.builder()
                    .username(username)
                    .password(password)
                    .cookieCount(cookieCount)
                    .build();
    }
}
