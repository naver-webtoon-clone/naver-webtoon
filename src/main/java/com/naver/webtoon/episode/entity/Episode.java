package com.naver.webtoon.episode.entity;

import com.naver.webtoon.common.time.Timestamped;
import com.naver.webtoon.webtoon.entity.Webtoon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Episode extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String postscript;

    @Column(nullable = false)
    private Integer views;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "needed_cookie_amount", nullable = false)
    private Integer neededCookieAmount;

    @Column(name = "free_release_date")
    private LocalDate freeReleaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="webtoon_id")
    private Webtoon webtoon;

    @Builder
    public Episode(Long id, String title, String content, String postscript, Integer views, Boolean isPublic, Integer neededCookieAmount, LocalDate freeReleaseDate, Webtoon webtoon) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postscript = postscript;
        this.views = views;
        this.isPublic = isPublic;
        this.neededCookieAmount = neededCookieAmount;
        this.freeReleaseDate = freeReleaseDate;
        this.webtoon = webtoon;
    }

    public void update(String title, String content, String postscript, Boolean isPublic, Integer neededCookieAmount, LocalDate freeReleaseDate) {
        this.title = title;
        this.content = content;
        this.postscript = postscript;
        this.isPublic = isPublic;
        this.neededCookieAmount = neededCookieAmount;
        this.freeReleaseDate = freeReleaseDate;
    }
}
