package com.spring.visti.domain.member.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.storybox.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberStoryRepository extends JpaRepository<MemberLikeStory, Long> {
    List<MemberLikeStory> findByMember(Member member);

    Optional<MemberLikeStory> findByMemberAndStory(Member member, Story story);
}
