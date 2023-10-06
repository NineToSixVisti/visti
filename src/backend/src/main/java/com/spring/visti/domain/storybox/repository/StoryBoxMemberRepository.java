package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.constant.Position;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoryBoxMemberRepository extends JpaRepository<StoryBoxMember, Long> {

    @Query("SELECT sbm FROM StoryBoxMember sbm JOIN FETCH sbm.storyBox WHERE sbm.member = :member")
    Page<StoryBoxMember> findByMember(Member member, Pageable pageable);

    @Query("SELECT sbm.storyBox FROM StoryBoxMember sbm WHERE sbm.member = :member AND sbm.storyBox.name LIKE %:keyword%")
    Page<StoryBox> findJoinedByMemberAndKeyword(Member member, String keyword, Pageable pageable);

    @Query("SELECT sbm FROM StoryBoxMember sbm " +
            "LEFT JOIN FETCH sbm.storyBox " +
            "WHERE sbm.member = :member AND sbm.position = :position")
    Page<StoryBoxMember> findByMemberAndPosition(Member member, Position position, Pageable pageable);

    Optional<StoryBoxMember> findByStoryBoxIdAndMember(Long storyBoxId, Member member);

    boolean existsByStoryBoxIdAndMember(Long storyBoxId, Member member);

}
