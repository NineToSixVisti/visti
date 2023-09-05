package com.spring.demo.domain.storybox.repository;

import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.domain.storybox.entity.Story;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByMember(Member member);
}
