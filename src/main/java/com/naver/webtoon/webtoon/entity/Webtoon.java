package com.naver.webtoon.webtoon.entity;

import com.naver.webtoon.common.time.Timestamped;
import com.naver.webtoon.webtoon.entity.enums.SerializedStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Webtoon extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "webtoon_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SerializedStatus serializedStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "recommended_webtoon_id")
    private RecommendedWebtoon recommendedWebtoon;

    @Builder
    public Webtoon(Long id, String title, String thumbnail, String description, SerializedStatus serializedStatus, Author author) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.serializedStatus = serializedStatus;
        this.author = author;
    }

    public void update(String title, String description, String thumbnail, SerializedStatus serializedStatus, Author author) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.serializedStatus = serializedStatus;
        this.author = author;
    }
}
