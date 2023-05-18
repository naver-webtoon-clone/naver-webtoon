package com.naver.webtoon.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebtoonException extends RuntimeException{

    private final ErrorCode errorCode;
}
