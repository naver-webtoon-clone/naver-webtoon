package com.naver.webtoon.comment.entity;

import com.naver.webtoon.comment.entity.enums.EmotionType;
import com.naver.webtoon.common.time.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static com.naver.webtoon.comment.entity.enums.EmotionType.DISLIKE;
import static com.naver.webtoon.comment.entity.enums.EmotionType.LIKE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReCommentEmotion extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "re_comment_emotion_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionType type;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="re_comment_id")
    private ReComment reComment;

    @Builder
    public ReCommentEmotion(Long id, EmotionType type, Long memberId, ReComment reComment) {
        this.id = id;
        this.type = type;
        this.memberId = memberId;
        this.reComment = reComment;
    }

    public static ReCommentEmotion createReCommentLike(Long memberId, ReComment reComment) {
        EmotionType like = LIKE;

        return ReCommentEmotion.builder()
                .type(like)
                .memberId(memberId)
                .reComment(reComment)
                .build();
    }

    public static ReCommentEmotion createReCommentDislike(Long memberId, ReComment reComment) {
        EmotionType dislike = DISLIKE;

        return ReCommentEmotion.builder()
                .type(dislike)
                .memberId(memberId)
                .reComment(reComment)
                .build();
    }
}
