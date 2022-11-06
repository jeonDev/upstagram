package com.api.upstagram.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.api.upstagram.domain.Story.StoryReaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.Story.Story;
import com.api.upstagram.domain.Story.StoryWatching;
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
    public ResponseVO<StoryRVO> registStory(@RequestPart StoryPVO pvo, @RequestPart MultipartFile file) throws IOException{
        log.info(this.getClass().getName() + " ==> Story Register!");
        ResponseVO<StoryRVO> r = new ResponseVO<StoryRVO>();
        
        pvo.setId(CommonUtils.getUserId());
        
        Story entity = storyService.registStory(pvo, file);
        StoryRVO rvo = StoryRVO.builder()
                        .storyNo(entity.getStoryNo())
                        .id(entity.getMember().getId())
                        .storyFileName(entity.getStoryFileName())
                        .storyTime(entity.getStoryTime())
                        .showYn(entity.getShowYn())
                        .keepYn(entity.getKeepYn())
                        .build();

        r.setData(rvo);

        return r;
    }

    /*
     * 스토리 반응 등록
     */
    @PostMapping("/user/story/reaction")
    public ResponseVO<StoryReactionRVO> storyReaction(@RequestBody StoryReactionPVO pvo) {
        log.info(this.getClass().getName() + " ==> Story Reaction Register!");
        ResponseVO<StoryReactionRVO> r = new ResponseVO<StoryReactionRVO>();

        pvo.setId(CommonUtils.getUserId());

        StoryReaction entity = storyService.storyReactionRegist(pvo);

        StoryReactionRVO rvo = StoryReactionRVO.builder()
                            .reactionNo(entity.getReactionNo())
                            .storyNo(entity.getStory().getStoryNo())
                            .id(entity.getId())
                            .storyLoveYn(entity.getStoryLoveYn())
                            .storyViewDate(entity.getStoryViewDate())
                            .build();

        r.setData(rvo);

        return r;
    }

     /*
      * 스토리 시청기록 등록
      */
    @PostMapping("/user/story/watch")
    public ResponseVO<StoryWatchingRVO> storyWatchingHistory(@RequestBody StoryWatchingPVO pvo){
        log.info(this.getClass().getName() + " ==> Story Watching Update!");
        ResponseVO<StoryWatchingRVO> r = new ResponseVO<StoryWatchingRVO>();

        pvo.setId(CommonUtils.getUserId());

        StoryWatching entity = storyService.storyWatchingHistory(pvo);
        StoryWatchingRVO rvo = StoryWatchingRVO.builder()
                                .storyWatchingNo(entity.getStoryWatchingNo())
                                .storyNo(entity.getStoryNo())
                                .id(entity.getId())
                                .firstWatchingDttm(entity.getRegDttm())
                                .lastWatchingDttm(entity.getLastDttm())
                                .build();

        r.setData(rvo);

        return r;
    }

    /*
     * 스토리 조회
     */
    @GetMapping("/user/story/list")
    public ResponseVO<List<StoryRVO>> getStoryList() {
        log.info(this.getClass().getName() + " ==> Follow's Story List Get!");
        ResponseVO<List<StoryRVO>> r = new ResponseVO<List<StoryRVO>>();
        
        StoryPVO pvo = new StoryPVO();
        pvo.setId(CommonUtils.getUserId());
        
        List<StoryRVO> rvo = storyService.selectFollowStoryList(pvo).stream()
                .map(m -> m.storyToRVO())
                .collect(Collectors.toList());
        
        r.setData(rvo);

        return r;
    }

    /*
     * 스토리 조회 (마이페이지)
     */
    @GetMapping("/user/my/story/list")
    public ResponseVO<List<StoryRVO>> getMyStoryList(@RequestParam(required = false) String id) {
        log.info(this.getClass().getName() + " ==> My Story List Get!");
        ResponseVO<List<StoryRVO>> r = new ResponseVO<List<StoryRVO>>();

        StoryPVO pvo = new StoryPVO();
        pvo.setId(id == null ? CommonUtils.getUserId() : id);

        List<StoryRVO> rvo = storyService.selectMyStoryList(pvo).stream()
                .map(m -> m.storyToRVO())
                .collect(Collectors.toList());
        
        r.setData(rvo);

        return r;
    }
}
