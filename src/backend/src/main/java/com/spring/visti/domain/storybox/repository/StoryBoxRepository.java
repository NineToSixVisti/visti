package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.storybox.entity.StoryBox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryBoxRepository  extends JpaRepository<StoryBox, Long> {
    Optional<StoryBox> findByToken(String token);
    Page<StoryBox> findByNameContaining(Pageable pageable, String keyword);
}
