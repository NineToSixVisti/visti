package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    Page<Story> findByMember(Member member, Pageable pageable);
}
