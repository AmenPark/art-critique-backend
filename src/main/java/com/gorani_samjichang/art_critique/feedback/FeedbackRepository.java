package com.gorani_samjichang.art_critique.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    Optional<FeedbackEntity> findBySerialNumber(String serialNumber);
}
