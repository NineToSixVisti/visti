package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryBoxMemberRepository extends JpaRepository<StoryBoxMember, Long> {

    Page<StoryBoxMember> findByMember(Member member, Pageable pageable);

    Optional<StoryBoxMember> findByStoryBoxAndMember(StoryBox storyBox, Member member);

}
