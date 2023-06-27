package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.common.exception.WebtoonException;
import com.naver.webtoon.member.entity.Member;
import com.naver.webtoon.webtoon.entity.InterestedWebtoon;
import com.naver.webtoon.webtoon.entity.Webtoon;
import com.naver.webtoon.webtoon.repository.InterestedWebtoonRepository;
import com.naver.webtoon.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.naver.webtoon.common.exception.ErrorCode.DUPLICATE_INTERESTED_WEBTOON;
import static com.naver.webtoon.common.exception.ErrorCode.NOT_FOUND_WEBTOON;

@Service
@RequiredArgsConstructor
public class InterestedWebtoonService {

    private final InterestedWebtoonRepository interestedWebtoonRepository;
    private final WebtoonRepository webtoonRepository;

    @Transactional
    public void registerInterestedWebtoon(Member currentMember, Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
                () -> new WebtoonException(NOT_FOUND_WEBTOON));
        throwIfDuplicatedInterestedWebtoon(currentMember, webtoon);

        InterestedWebtoon interestedWebtoon = InterestedWebtoon.createInterestedWebtoon(currentMember, webtoon);
        interestedWebtoonRepository.save(interestedWebtoon);
    }

    private void throwIfDuplicatedInterestedWebtoon(Member member, Webtoon webtoon) {
        if (interestedWebtoonRepository.existsByMemberAndWebtoon(member, webtoon)) {
            throw new WebtoonException(DUPLICATE_INTERESTED_WEBTOON);
        }
    }
}
