package com.spring.demo.domain.storybox.repository;

import com.spring.demo.domain.storybox.entity.Story;
import com.spring.demo.domain.storybox.entity.StoryBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryBoxRepository  extends JpaRepository<StoryBox, Long> {

}
