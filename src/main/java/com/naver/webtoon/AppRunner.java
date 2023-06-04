package com.naver.webtoon;

import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final WebtoonService webtoonService;

    @Override
    public void run(String... args) throws Exception {
    }
}
