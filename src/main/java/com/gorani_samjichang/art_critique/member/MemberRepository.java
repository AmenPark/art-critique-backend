package com.gorani_samjichang.art_critique.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    MemberEntity findByEmail(String email);
}