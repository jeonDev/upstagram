package com.api.upstagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.service.StoryService;
import com.api.upstagram.vo.Story.StoryPVO;
import com.api.upstagram.vo.Story.StoryRVO;
import com.api.upstagram.vo.Story.StoryReactionPVO;
import com.api.upstagram.vo.Story.StoryReactionRVO;
import com.api.upstagram.vo.Story.StoryWatchingPVO;
import com.api.upstagram.vo.Story.StoryWatchingRVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class StoryController {

    @Autowired
    private StoryService storyService;

    /*
     * 스토리 등록
     */
    @PostMapping("/user/story/regist")
    public ResponseVO<StoryRVO> registStory(@RequestBody StoryPVO pvo, @RequestPart MultipartFile file){
        log.info(this.getClass().getName() + " ==> Story Register!");
        ResponseVO<StoryRVO> r = new ResponseVO<StoryRVO>();

        storyService.registStory(pvo, file);

        return r;
    }

    /*
     * 스토리 반응 등록
     */
    @PostMapping("/user/story/reaction")
    public ResponseVO<StoryReactionRVO> storyReaction(@RequestBody StoryReactionPVO pvo) {
        log.info(this.getClass().getName() + " ==> Story Reaction Register!");
        ResponseVO<StoryReactionRVO> r = new ResponseVO<StoryReactionRVO>();

        storyService.storyReactionRegist(pvo);

        return r;
    }

     /*
      * 스토리 시청기록 등록
      */
    @PostMapping("/user/story/history")
    public ResponseVO<StoryWatchingRVO> storyWatchingHistory(@RequestBody StoryWatchingPVO pvo){
        log.info(this.getClass().getName() + " ==> Story Watching Update!");
        ResponseVO<StoryWatchingRVO> r = new ResponseVO<StoryWatchingRVO>();

        pvo.setId(CommonUtils.getUserId());

        storyService.storyWatchingHistory(pvo);

        return r;
    }
}
