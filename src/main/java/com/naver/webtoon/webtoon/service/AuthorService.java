package com.naver.webtoon.webtoon.service;

import com.naver.webtoon.webtoon.dto.request.AuthorRegisterRequest;
import com.naver.webtoon.webtoon.entity.Author;
import com.naver.webtoon.webtoon.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public void registerAuthor(AuthorRegisterRequest request) {
        Author author = request.toAuthor();

        authorRepository.save(author);
    }
}
