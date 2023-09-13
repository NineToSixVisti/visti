package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.storybox.constant.Position;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryBoxMemberRepository extends JpaRepository<StoryBoxMember, Long> {

    Page<StoryBoxMember> findByMember(Member member, Pageable pageable);

    Page<StoryBoxMember> findByMemberAndPosition(Member member, Position position, Pageable pageable);

    Optional<StoryBoxMember> findByStoryBoxIdAndMember(Long storyBoxId, Member member);

    boolean existsByStoryBoxIdAndMember(Long storyBoxId, Member member);
}
