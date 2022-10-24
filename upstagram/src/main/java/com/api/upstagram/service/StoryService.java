package com.api.upstagram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    /*
     * 스토리 등록
     */
    public StoryEntity registStory(StoryPVO pvo, MultipartFile file) {
        log.info(this.getClass().getName() + " => Story Register!");

        /*
         * 1. 파라미터 검증
         * 2. 파일 업로드
         * 3. 스토리 등록
         */
        return null;
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
}
