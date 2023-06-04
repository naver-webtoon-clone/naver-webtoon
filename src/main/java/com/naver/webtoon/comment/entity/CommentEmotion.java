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
public class CommentEmotion extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_emotion_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionType type;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id")
    private Comment comment;

    @Builder
    public CommentEmotion(Long id, EmotionType type, Comment comment, Long memberId) {
        this.id = id;
        this.type = type;
        this.memberId = memberId;
        this.comment = comment;
    }

    public static CommentEmotion createCommentLike(Long memberId, Comment comment) {
        EmotionType like = LIKE;

        return CommentEmotion.builder()
                .type(like)
                .memberId(memberId)
                .comment(comment)
                .build();
    }

    public static CommentEmotion createCommentDislike(Long memberId, Comment comment) {
        EmotionType dislike = DISLIKE;

        return CommentEmotion.builder()
                .type(dislike)
                .memberId(memberId)
                .comment(comment)
                .build();
    }
}
