package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.webtoon.dto.request.PublishingDayRegisterRequest;
import com.naver.webtoon.webtoon.entity.PublishingDay;
import com.naver.webtoon.webtoon.repository.PublishingDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublishingDayService {

    private final PublishingDayRepository publishingDayRepository;

    @Transactional
    public void registerPublishingDay(PublishingDayRegisterRequest request) {
        PublishingDay publishingDay = request.toPublishingDay();

        publishingDayRepository.save(publishingDay);
    }
}
