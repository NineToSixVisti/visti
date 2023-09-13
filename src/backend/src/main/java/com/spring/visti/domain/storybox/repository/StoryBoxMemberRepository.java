package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryBoxMemberRepository extends JpaRepository<StoryBoxMember, Long> {

    Optional<StoryBoxMember> findByStoryBoxAndMember(StoryBox storyBox, Member member);

    Optional<StoryBoxMember> findByStoryBoxIdAndMember(Long storyBoxId, Member member);

}
