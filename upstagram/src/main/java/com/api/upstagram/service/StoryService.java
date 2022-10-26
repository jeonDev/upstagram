package com.api.upstagram.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Story.StoryEntity;
import com.api.upstagram.domain.Story.StoryReactionEntity;
import com.api.upstagram.domain.Story.StoryReactionRepository;
import com.api.upstagram.domain.Story.StoryRepository;
import com.api.upstagram.domain.Story.StoryWatchingEntity;
import com.api.upstagram.vo.Story.StoryPVO;
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

    @Value("${spring.servlet.multipart.location}")
    String filePath;

    /*
     * 스토리 등록
     */
    public StoryEntity registStory(StoryPVO pvo, MultipartFile file) {
        log.info(this.getClass().getName() + " => Story Register!");

        /*
         * 1. 파라미터 검증
         * 2. 파일 업로드
         *  2-1. 동영상 : 용량체크 / 시간체크 -> storyTime
         *  2-2. 이미지 : 이미지 to 동영상 -> 5초
         * 3. 스토리 등록
         */
        // 1. 파라미터 검증
        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "로그인 후에 이용해주세요.");
        if(file == null || file.isEmpty()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "동영상 or 이미지를 등록해주세요.");

        String[] exts = {"image/png", "image/jpg", "image/jpeg", ".video/mp4", ".video/avi"};
        String fileName;

        try{
            // TODO: 파일이 이미지일 경우, storyTime setting

            // 파일 업로드
            fileName = CommonUtils.uploadFile(file, filePath, exts);
            pvo.setStoryFileName(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String showYn = pvo.getShowYn() != null ? pvo.getShowYn() : "Y";    // 표시여부
        String keepYn = pvo.getKeepYn() != null ? pvo.getKeepYn() : "N";    // 보관여부
        
        StoryEntity storyEntity = StoryEntity.builder()
                                    .id(pvo.getId())
                                    .storyFileName(pvo.getStoryFileName())
                                    .storyTime("")
                                    .showYn(showYn)
                                    .keepYn(keepYn)
                                    .build();
        
        storyRepository.save(storyEntity);


        return storyEntity;
    }

    /*
     * 스토리 반응 등록
     */
    public StoryReactionEntity storyReactionRegist(StoryReactionPVO pvo) {
        log.info(this.getClass().getName() + " => Story Reaction Register!");

        /*
         * 1. 파라미터 검증
         *  1-1. 스토리 존재여부 체크
         * 2. 스토리 반응 Select
         *  2-1. insert & delete
         */
        return null;
    }

    /*
     * 스토리 시청기록 등록
     */
    public StoryWatchingEntity storyWatchingHistory(StoryWatchingPVO pvo) {
        log.info(this.getClass().getName() + " => Story Watching Update!");

        /*
         * 1. 파라미터 검증
         *  1-1. 스토리 존재여부 체크
         * 2. save (insert & update - LAST_DTTM)
         */
        return null;
    }

    /*
     * 스토리 조회
     */
    public List<StoryEntity> getStoryList(StoryPVO pvo) {
        /*
         * param : FOLLOW_USER.ID 
         * WHERE STORY.ID IN FOLLOW_USER.FOLLOW_ID -> MEMBER_INFO.USE_YN : Y
         *   AND SHOW_YN
         *   AND REG_DTTM > DATE_ADD(NOW(), INTERVAL - 1 DAY)
         */
        List<StoryEntity> list = storyRepository.findAll();
        return list;
    }
}
