package com.api.upstagram.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.api.upstagram.common.vo.FileInfo;
import com.api.upstagram.vo.Story.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Story.Story;
import com.api.upstagram.domain.Story.StoryReaction;
import com.api.upstagram.domain.Story.StoryReactionRepository;
import com.api.upstagram.domain.Story.StoryRepository;
import com.api.upstagram.domain.Story.StoryWatching;
import com.api.upstagram.domain.Story.StoryWatchingRepository;
import com.api.upstagram.domain.MemberInfo.MemberInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoryService {

    private final StoryRepository storyRepository;

    private final StoryReactionRepository storyReactionRepository;

    private final StoryWatchingRepository storyWatchingRepository;

    @Value("${resource-path}")
    private String resourePath;

    /*
     * 스토리 등록
     */
    public Story registStory(StoryPVO pvo, MultipartFile file) throws IOException {
        log.info(this.getClass().getName() + " => Story Register!");

        // 1. 파라미터 검증
        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "로그인 후에 이용해주세요.");
        if(file == null || file.isEmpty()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "동영상 or 이미지를 등록해주세요.");

        // 2. 파일 업로드
        String[] exts = {"image/png", "image/jpg", "image/jpeg", "video/mp4", "video/avi"};

        FileInfo fileInfo = CommonUtils.uploadFile(file, resourePath, exts);
        pvo.setStoryFileName(fileInfo.getFileName());

        // 3. Story 등록
        String showYn = pvo.getShowYn() != null ? pvo.getShowYn() : "Y";    // 표시여부
        String keepYn = pvo.getKeepYn() != null ? pvo.getKeepYn() : "N";    // 보관여부
        
        MemberInfo member = MemberInfo.builder()
                                    .id(pvo.getId())
                                    .build();

        Story storyEntity = Story.builder()
                                    .member(member)
                                    .storyFileName(pvo.getStoryFileName())
                                    .storyTime(pvo.getStoryTime())
                                    .showYn(showYn)
                                    .keepYn(keepYn)
                                    .build();
        
        return storyRepository.save(storyEntity);
    }

    /*
     * 스토리 조회
     */
    public List<Story> selectFollowStoryList(StoryPVO pvo) {
        return storyRepository.selectFollowStoryList(pvo.getId(), CommonUtils.dateNowDaysCalculator(-1));
    }

    /*
     * 스토리 조회 (마이페이지)
     */
    public List<Story> selectMyStoryList(StoryPVO pvo) {
        log.info(this.getClass().getName() + " => My Story List Select!");
        return storyRepository.selectMyStoryList(pvo.getId(), CommonUtils.dateNowDaysCalculator(-1));
    }

    /*
     * 스토리 반응 등록
     */
    public StoryReaction storyReactionRegist(StoryReactionPVO pvo) {
        log.info(this.getClass().getName() + " => Story Reaction Register!");

        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "로그인 후에 이용해주세요.");

        // 1. Story 있는지 체크
        Optional<Story> storyEntity = storyRepository.findById(pvo.getStoryNo());
        if(!storyEntity.isPresent()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());

        Story story = storyEntity.get();

        MemberInfo member = MemberInfo.builder()
                .id(pvo.getId())
                .build();

        // 2. Story Reaction 있는지 체크
        Optional<StoryReaction> storyReaction = storyReactionRepository.findByStoryAndMember(story, member);

        // 3-1. 이전내역이 없을 경우, 좋아요 표시.
        if(!storyReaction.isPresent()) {
            return storyReactionRepository.save(StoryReaction.builder()
                    .story(story)
                    .member(member)
                    .storyLoveYn("Y")
                    .storyViewDate(LocalDateTime.now())
                    .build());

        // 3-2. 이전내역이 있을 경우, 좋아요 취소 표시.
        } else {
            StoryReaction entity = storyReaction.get();
            String storyLoveYn = "N".equals(entity.getStoryLoveYn()) ? "Y" : "N";

            return storyReactionRepository.save(entity.updateStoryReaction(storyLoveYn));
        }
    }

    /*
    * 스토리 반응 조회
    * */
    public List<StoryReaction> selectStoryReactionList(StoryReactionPVO pvo) {
        return storyReactionRepository.selectStoryReactionList(pvo.getStoryNo());
    }

    /*
     * 스토리 시청기록 등록
     */
    public StoryWatching storyWatchingHistory(StoryWatchingPVO pvo) {
        log.info(this.getClass().getName() + " => Story Watching Update!");

        Story story = Story.builder()
                .storyNo(pvo.getStoryNo())
                .build();

        MemberInfo member = MemberInfo.builder()
                .id(pvo.getId())
                .build();

        Optional<StoryWatching> storyWatchingEntity = storyWatchingRepository.findByStoryAndMember(story, member);

        // 시청내역이 있을 경우 최종수정일만 Update
        if(storyWatchingEntity.isPresent()) {
            StoryWatching entity = storyWatchingEntity.get();
            entity.setLastDttm(LocalDateTime.now());
            return storyWatchingRepository.save(entity);
        // 시청내역이 없을 경우 시청내역 Insert
        } else {
            return storyWatchingRepository.save(StoryWatching.builder()
                    .story(Story.builder()
                            .storyNo(pvo.getStoryNo())
                            .build())
                    .member(member)
                    .build());
        }
    }

    /*
    * 스토리 시청기록 조회
    * */
    public List<StoryWatching> selectWatchingHistoryList(StoryWatchingPVO pvo) {
        return storyWatchingRepository.selectWatchingList(pvo.getStoryNo());
    }

}
