package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.webtoon.dto.request.HashTagRegisterRequest;
import com.naver.webtoon.webtoon.entity.HashTag;
import com.naver.webtoon.webtoon.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    @Transactional
    public void registerHashTag(HashTagRegisterRequest request) {
        HashTag hashTag = request.toHashTag();

        hashTagRepository.save(hashTag);
    }
}
