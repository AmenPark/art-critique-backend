package com.gorani_samjichang.art_critique.member;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gorani_samjichang.art_critique.feedback.FeedbackEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
//    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private SNSMapEntity snsMap;
    private String email;
    private String password;
    private Boolean is_deleted;
    private String nickname;
    private Integer credit;
    @Column(columnDefinition = "TEXT")
    private String profile;
    private String level;
    private LocalDateTime created_at;
    private String role; // USER, ADMIN 이정도만 생각하는중
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<FeedbackEntity> feedbacks = new ArrayList<>();

    public void addFeedback(FeedbackEntity feedbackEntity) {
        feedbacks.add(feedbackEntity);
        feedbackEntity.setMemberEntity(this);
    }

    public void removeFeedback(FeedbackEntity feedbackEntity) {
        feedbacks.remove(feedbackEntity);
        feedbackEntity.setMemberEntity(null);
    }
}
