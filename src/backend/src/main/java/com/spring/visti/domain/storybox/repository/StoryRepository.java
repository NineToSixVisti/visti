package com.spring.visti.domain.storybox.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    Page<Story> findByMember(Member member, Pageable pageable);

    Page<Story> findByStoryBox(StoryBox storyBox, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Story s WHERE s.member.id = :memberId AND DATE(s.createdAt) = CURRENT_DATE")
    int countTodayStoriesByMemberId(@Param("memberId") Long memberId);
}
