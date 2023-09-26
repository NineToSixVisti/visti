package com.spring.visti.domain.member.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryIncludeLikeDTO;
import com.spring.visti.domain.storybox.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberLikeStoryRepository extends JpaRepository<MemberLikeStory, Long> {
    Page<MemberLikeStory> findByMember(Member member, Pageable pageable);

    Optional<MemberLikeStory> findByMemberAndStory(Member member, Story story);


    @Query(value = "SELECT s FROM Story s LEFT JOIN MemberLikeStory mls ON s.id = mls.story.id AND mls.member.id = :memberId ORDER BY RAND()LIMIT :count", nativeQuery = true)
    List<Story> findRandomStoriesByMember(@Param("memberId") Long memberId, @Param("count") int count);

    @Query(value = "SELECT NEW com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryIncludeLikeDTO(s, CASE WHEN mls.member.id = :memberId THEN true ELSE false END) " +
            "FROM Story s LEFT JOIN MemberLikeStory mls ON s.id = mls.story.id " +
            "LEFT JOIN FETCH s.storyBox " +
            "WHERE s.member.id = :memberId")
    List<StoryIncludeLikeDTO> findStoriesWithLikeInfo(@Param("memberId") Long memberId);

    @Query(value = "SELECT NEW com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryIncludeLikeDTO(s, CASE WHEN mls.member.id = :memberId THEN true ELSE false END) " +
            "FROM Story s LEFT JOIN MemberLikeStory mls ON s.id = mls.story.id " +
            "LEFT JOIN FETCH s.storyBox " +
            "WHERE s.member.id = :memberId ORDER BY RAND()LIMIT 10")
    List<StoryIncludeLikeDTO> findRandomStoriesWithLikeInfo(@Param("memberId") Long memberId);

    @Query(value = "SELECT s FROM Story s LEFT JOIN MemberLikeStory mls ON s.id = mls.story.id AND mls.member.id = :memberId")
    List<Story> findStoriesByMember(@Param("memberId") Long memberId);


    boolean existsByMemberIdAndStoryId(Long memberId, Long storyId);

    void deleteByMemberIdAndStoryId(Long memberId, Long storyId);
}
