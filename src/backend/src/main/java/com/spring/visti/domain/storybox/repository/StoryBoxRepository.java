package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.storybox.entity.StoryBox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StoryBoxRepository  extends JpaRepository<StoryBox, Long> {
    Optional<StoryBox> findByToken(String token);

    List<StoryBox> findByFinishedAtBetween(LocalDateTime start, LocalDateTime end);

    List<StoryBox> findAllByFinishedAtBeforeAndBlindIsTrue(LocalDateTime time);
}
