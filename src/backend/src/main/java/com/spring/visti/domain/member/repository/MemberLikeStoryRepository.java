package com.spring.visti.domain.member.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.storybox.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberLikeStoryRepository extends JpaRepository<MemberLikeStory, Long> {
    Page<MemberLikeStory> findByMember(Member member, Pageable pageable);

    Optional<MemberLikeStory> findByMemberAndStory(Member member, Story story);

    boolean existsByMemberAndStory(Member member, Story story);
}
