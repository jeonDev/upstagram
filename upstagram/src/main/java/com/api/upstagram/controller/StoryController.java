package com.api.upstagram.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseVO<StoryRVO> registStory(@RequestPart StoryPVO pvo, @RequestPart MultipartFile file) {
        log.info(this.getClass().getName() + " ==> Story Register!");
        ResponseVO<StoryRVO> r = new ResponseVO<StoryRVO>();
        
        pvo.setId(CommonUtils.getUserId());
        
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

    /*
     * 스토리 조회
     */
    @GetMapping("/user/story/list")
    public ResponseVO<List<StoryRVO>> getStoryList() {
        log.info(this.getClass().getName() + " ==> Story Watching Update!");
        ResponseVO<List<StoryRVO>> r = new ResponseVO<List<StoryRVO>>();
        
        StoryPVO pvo = new StoryPVO();
        pvo.setId(CommonUtils.getUserId());
        
        List<StoryRVO> rvo = storyService.getStoryList(pvo).stream()
                            .map(m -> StoryRVO.builder()
                                    .storyNo(m.getStoryNo())
                                    .id(m.getId())
                                    .storyTime(m.getStoryTime())
                                    .storyFileName(m.getStoryFileName())
                                    .showYn(m.getShowYn())
                                    .keepYn(m.getKeepYn())
                                    .member(null)
                                    .build())
                            .collect(Collectors.toList());
        
        r.setData(rvo);

        return r;
    }

}
