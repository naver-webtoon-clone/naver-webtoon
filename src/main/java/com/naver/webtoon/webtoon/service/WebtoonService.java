package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.webtoon.dto.request.WebtoonRegisterRequest;
import com.naver.webtoon.webtoon.dto.request.WebtoonUpdateRequest;
import com.naver.webtoon.webtoon.dto.response.WebtoonInfoListResponse;
import com.naver.webtoon.webtoon.entity.Author;
import com.naver.webtoon.webtoon.entity.HashTag;
import com.naver.webtoon.webtoon.entity.PublishingDay;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.entity.WebtoonHashTag;
import com.naver.webtoon.webtoon.entity.WebtoonPublishingDay;
import com.naver.webtoon.webtoon.entity.enums.DayOfTheWeek;
import com.naver.webtoon.webtoon.entity.enums.SerializedStatus;
import com.naver.webtoon.webtoon.repository.AuthorRepository;
import com.naver.webtoon.webtoon.repository.HashTagRepository;
import com.naver.webtoon.webtoon.repository.PublishingDayRepository;
import com.naver.webtoon.webtoon.repository.WebtoonHashTagRepository;
import com.naver.webtoon.webtoon.repository.WebtoonPublishingDayRepository;
import com.naver.webtoon.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_AUTHOR;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_HASH_TAG;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_PUBLISHING_DAY;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_WEBTOON;

@Service
@RequiredArgsConstructor
public class WebtoonService {

    private final AuthorRepository authorRepository;
    private final WebtoonRepository webtoonRepository;
    private final PublishingDayRepository publishingDayRepository;
    private final WebtoonPublishingDayRepository webtoonPublishingDayRepository;
    private final HashTagRepository hashTagRepository;
    private final WebtoonHashTagRepository webtoonHashTagRepository;

    @Transactional
    public void registerWebtoon(WebtoonRegisterRequest request) {
        Author author = authorRepository.findByName(request.getAuthor()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_AUTHOR));
        Webtoon webtoon = request.toWebtoon(author);

        webtoonRepository.save(webtoon);
        saveWebtoonPublishingDay(webtoon, request);
        saveWebtoonHashTag(webtoon, request);
    }

    private void saveWebtoonPublishingDay(Webtoon webtoon, WebtoonRegisterRequest request) {
        for (String dayOfTheWeek : request.getPublishingDay()) {
            DayOfTheWeek dayOfTheWeekEnum = DayOfTheWeek.toEnum(dayOfTheWeek);
            PublishingDay publishingDay = publishingDayRepository.findByDayOfTheWeek(dayOfTheWeekEnum).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_PUBLISHING_DAY));
            WebtoonPublishingDay webtoonPublishingDay = request.toWebtoonPublishingDay(webtoon, publishingDay);

            webtoonPublishingDayRepository.save(webtoonPublishingDay);
        }
    }

    private void saveWebtoonHashTag(Webtoon webtoon, WebtoonRegisterRequest request) {
        for (String name : request.getHashTag()) {
            HashTag hashTag = hashTagRepository.findByName(name).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_HASH_TAG));
            WebtoonHashTag webtoonHashTag = request.toWebtoonHashTag(webtoon,hashTag);

            webtoonHashTagRepository.save(webtoonHashTag);
        }
    }

    @Transactional
    public void updateWebtoon(Long webtoonId, WebtoonUpdateRequest request) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));

        updateWebtoon(webtoon, request);
        updateWebtoonPublishingDay(webtoon, request);
        updateWebtoonHashTag(webtoon, request);
    }

    private void updateWebtoon(Webtoon webtoon, WebtoonUpdateRequest request) {
        String title = request.getTitle();
        String description = request.getDescription();
        String thumbnail = request.getThumbnail();
        SerializedStatus serializedStatus = SerializedStatus.toEnum(request.getSerializedStatus());
        Author author = authorRepository.findByName(request.getAuthor()).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_AUTHOR));

        webtoon.update(title, description, thumbnail, serializedStatus, author);
    }

    private void updateWebtoonPublishingDay(Webtoon webtoon, WebtoonUpdateRequest request) {
        webtoonPublishingDayRepository.deleteByWebtoonId(webtoon.getId());
        saveWebtoonPublishingDay(webtoon, request);
    }

    private void saveWebtoonPublishingDay(Webtoon webtoon, WebtoonUpdateRequest request) {
        for (String dayOfTheWeek : request.getPublishingDay()) {
            DayOfTheWeek dayOfTheWeekEnum = DayOfTheWeek.toEnum(dayOfTheWeek);
            PublishingDay publishingDay = publishingDayRepository.findByDayOfTheWeek(dayOfTheWeekEnum).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_PUBLISHING_DAY));
            WebtoonPublishingDay webtoonPublishingDay = request.toWebtoonPublishingDay(webtoon, publishingDay);

            webtoonPublishingDayRepository.save(webtoonPublishingDay);
        }
    }

    private void updateWebtoonHashTag(Webtoon webtoon, WebtoonUpdateRequest request) {
        webtoonHashTagRepository.deleteByWebtoonId(webtoon.getId());
        saveWebtoonHashTag(webtoon, request);
    }

    private void saveWebtoonHashTag(Webtoon webtoon, WebtoonUpdateRequest request) {
        for (String name : request.getHashTag()) {
            HashTag hashTag = hashTagRepository.findByName(name).orElseThrow(
                    () -> new WebtoonException(NOT_FOUND_HASH_TAG));
            WebtoonHashTag webtoonHashTag = request.toWebtoonHashTag(webtoon,hashTag);

            webtoonHashTagRepository.save(webtoonHashTag);
        }
    }

    @Transactional
    public void deleteWebtoon(Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));
        webtoonHashTagRepository.deleteByWebtoonId(webtoon.getId());
        webtoonPublishingDayRepository.deleteByWebtoonId(webtoon.getId());
        webtoonRepository.delete(webtoon);
    }

    @Transactional(readOnly = true)
    public WebtoonInfoListResponse getPopularWebtoonsByDayOfWeek(String publishingDay){
        DayOfTheWeek dayOfTheWeek = DayOfTheWeek.toEnum(publishingDay);
        List<Webtoon> webtoons = webtoonRepository.findOnGoingWebtoonByDayOfTheWeek(dayOfTheWeek);
        return WebtoonInfoListResponse.toResponse(webtoons);
    }
}
