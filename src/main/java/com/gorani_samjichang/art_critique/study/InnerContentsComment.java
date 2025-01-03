package com.gorani_samjichang.art_critique.study;

import com.gorani_samjichang.art_critique.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inner_contents_comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InnerContentsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reid;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "serialNumber")
    private MemberEntity member;

    private String comment;
    private Boolean likes;
    private LocalDateTime createdAt;
}
