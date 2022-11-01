package com.api.upstagram.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.api.upstagram.common.vo.FileInfo;
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
import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;
import com.api.upstagram.vo.Story.StoryInterface;
import com.api.upstagram.vo.Story.StoryPVO;
import com.api.upstagram.vo.Story.StoryRVO;
import com.api.upstagram.vo.Story.StoryReactionPVO;
import com.api.upstagram.vo.Story.StoryWatchingPVO;

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
        String fileName;

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
     * 스토리 반응 등록
     */
    public StoryReaction storyReactionRegist(StoryReactionPVO pvo) {
        log.info(this.getClass().getName() + " => Story Reaction Register!");

        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "로그인 후에 이용해주세요.");

        // 1. Story 있는지 체크
        Optional<Story> storyEntity = storyRepository.findById(pvo.getStoryNo());
        if(!storyEntity.isPresent()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());

        Story story = storyEntity.get();

        // 2. Story Reaction 있는지 체크
        Optional<StoryReaction> storyReaction = storyReactionRepository.findByStoryAndId(story, pvo.getId());

        // 3-1. 이전내역이 없을 경우, 좋아요 표시.
        if(!storyReaction.isPresent()) {
            return storyReactionRepository.save(StoryReaction.builder()
                    .story(story)
                    .id(pvo.getId())
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
     * 스토리 시청기록 등록
     */
    public StoryWatching storyWatchingHistory(StoryWatchingPVO pvo) {
        log.info(this.getClass().getName() + " => Story Watching Update!");

        Optional<StoryWatching> storyWatchingEntity = storyWatchingRepository.findByStoryNoAndId(pvo.getStoryNo(), pvo.getId());

        // 시청내역이 있을 경우 최종수정일만 Update
        if(storyWatchingEntity.isPresent()) {
            StoryWatching entity = storyWatchingEntity.get();
            entity.setLastDttm(LocalDateTime.now());
            return storyWatchingRepository.save(entity);
        // 시청내역이 없을 경우 시청내역 Insert
        } else {
            return storyWatchingRepository.save(StoryWatching.builder()
                    .storyNo(pvo.getStoryNo())
                    .id(pvo.getId())
                    .build());
        }
    }

    /*
     * 스토리 조회
     */
    public List<StoryRVO> getFollowStoryList(StoryPVO pvo) {
        log.info(this.getClass().getName() + " => Follow's Story List Get!");
        List<StoryRVO> list = this.getFollowStoryListImpl(pvo).stream()
                                .map(m -> StoryRVO.builder()
                                    .storyNo(m.getStoryNo())
                                    .id(m.getId())
                                    .storyFileName(m.getStoryFileName())
                                    .storyTime(m.getStoryTime())
                                    .showYn(m.getShowYn())
                                    .keepYn(m.getKeepYn())
                                    .member(MemberInfoRVO.builder()
                                        .id(m.getFollowId())
                                        .name(m.getFollowName())
                                        .nickname(m.getFollowNickname())
                                        .sex(m.getFollowSex())
                                        .tel(m.getFollowTel())
                                        .oauthNo(m.getFollowOauthNo())
                                        .build())   
                                    .build())
                                .collect(Collectors.toList());

        return list;
    }

    /* Story 조회(Follow) */
    public List<StoryInterface> getFollowStoryListImpl(StoryPVO pvo) {
        return storyRepository.findByFollowStoryList(pvo.getId());
    }

    /*
     * 스토리 조회 (마이페이지)
     */
    public List<StoryRVO> getMyStoryList(StoryPVO pvo) {
        log.info(this.getClass().getName() + " => My Story List Get!");
        List<StoryRVO> list = this.getMyStoryListImpl(pvo).stream()
                                .map(m -> StoryRVO.builder()
                                    .storyNo(m.getStoryNo())
                                    .id(m.getId())
                                    .storyFileName(m.getStoryFileName())
                                    .storyTime(m.getStoryTime())
                                    .showYn(m.getShowYn())
                                    .keepYn(m.getKeepYn())
                                    .member(MemberInfoRVO.builder()
                                        .id(m.getFollowId())
                                        .name(m.getFollowName())
                                        .nickname(m.getFollowNickname())
                                        .sex(m.getFollowSex())
                                        .tel(m.getFollowTel())
                                        .oauthNo(m.getFollowOauthNo())
                                        .build())   
                                    .build())
                                .collect(Collectors.toList());

        return list;
    }

    /* Story 조회(My) */
    public List<StoryInterface> getMyStoryListImpl(StoryPVO pvo) {
        return storyRepository.findByMyStoryList(pvo.getMyId());
    }
}
