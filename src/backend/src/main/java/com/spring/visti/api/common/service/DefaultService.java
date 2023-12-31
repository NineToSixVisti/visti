package com.spring.visti.api.common.service;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.member.service.CustomUserDetails;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.repository.StoryBoxRepository;
import com.spring.visti.domain.storybox.repository.StoryRepository;
import com.spring.visti.utils.exception.ApiException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.spring.visti.utils.exception.ErrorCode.*;


public interface DefaultService {

    default Member getMemberBySecurity(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((CustomUserDetails) authentication.getPrincipal()).getMember();
        }
        throw new ApiException(NO_MEMBER_ERROR);
    }

    default Member getMember(String email, MemberRepository memberRepository) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isEmpty()) { throw new ApiException(NO_MEMBER_ERROR); }
        return optionalMember.get();
    }

    default Story getStory(Long storyId, StoryRepository storyRepository) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        if (optionalStory.isEmpty()) { throw new ApiException(NO_STORY_ERROR); }
        return optionalStory.get();
    }

    default StoryBox getStoryBox(Long storyBoxId, StoryBoxRepository storyBoxRepository) {
        Optional<StoryBox> optionalStoryBox = storyBoxRepository.findById(storyBoxId);
        if (optionalStoryBox.isEmpty()) { throw new ApiException(NO_STORY_BOX_ERROR); }
        return optionalStoryBox.get();
    }


}