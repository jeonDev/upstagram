package com.api.upstagram.controller;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.Feed.Entity.Feed;
import com.api.upstagram.domain.Feed.Entity.FeedHeart;
import com.api.upstagram.service.FeedService;
import com.api.upstagram.vo.Feed.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class FeedController {

    @Autowired
    private FeedService feedService;

    /*
    * Feed 등록
    * */
    @PostMapping("/user/feed/regist")
    public ResponseVO<FeedRVO> writeFeed(@RequestPart FeedPVO pvo, @RequestPart MultipartFile[] files) throws IOException {
        log.info(this.getClass().getName() + " ==> Feed 등록!");
        ResponseVO<FeedRVO> r = new ResponseVO<FeedRVO>();
        pvo.setId(CommonUtils.getUserId());

        FeedRVO rvo = feedService.insertFeed(pvo, files)
                .feedToRVO();

        r.setData(rvo);

        return r;
    }

    /*
     * Feed 조회
     * */
    @GetMapping("/user/feed/list")
    public ResponseVO<List<FeedRVO>> feedList(FeedListPVO pvo) {
        log.info(this.getClass().getName() + " ==> Feed List 조회");
        log.info(pvo.toString());
        ResponseVO<List<FeedRVO>> r = new ResponseVO<List<FeedRVO>>();

        pvo.setId(CommonUtils.getUserId());

        List<FeedRVO> rvo = feedService.selectFeedList(pvo);

        r.setData(rvo);

        return r;
    }

    /*
    * Feed Tag 조회
    * */
    @GetMapping("/user/feed/tag/list")
    public ResponseVO<List<FeedTagRVO>> feedTagList(@RequestParam Long feedNo) {
        log.info(this.getClass().getName() + " ==> Feed Tag List 조회" + feedNo);
        ResponseVO<List<FeedTagRVO>> r = new ResponseVO<List<FeedTagRVO>>();

        List<FeedTagRVO> rvo = feedService.selectFeedTagList(feedNo);

        r.setData(rvo);

        return r;
    }

    /*
    * Feed 수정
    * */
    @PostMapping("/user/feed/update")
    public ResponseVO<FeedRVO> updateFeed(@RequestPart FeedPVO pvo, @RequestPart(required = false) MultipartFile[] files)
            throws IOException {
        log.info(this.getClass().getName() + " ==> Feed 수정!");
        ResponseVO<FeedRVO> r = new ResponseVO<FeedRVO>();

        // TODO: Feed 수정처리
        Feed feed = feedService.updateFeed(pvo, files);

        return r;
    }

    /*
    * Feed 좋아요 기능
    * */
    @PostMapping("/user/feed/heart/save")
    public ResponseVO<FeedHeartRVO> feedHeart(@RequestBody FeedHeartPVO pvo) {
        log.info(this.getClass().getName() + " ==> Feed 좋아요!");
        ResponseVO<FeedHeartRVO> r = new ResponseVO<FeedHeartRVO>();

        pvo.setId(CommonUtils.getUserId());

        FeedHeart feedHeart = feedService.feedHeartSave(pvo);

        FeedHeartRVO rvo = feedHeart != null ? feedHeart.feedHeartToRVO() : FeedHeartRVO.builder().build();

        int feedHeartCnt = feedService.selectFeedHeartCnt(pvo.getFeedNo());
        rvo.setFeedHeartCnt(feedHeartCnt);

        r.setData(rvo);

        return r;
    }

    /*
    * Feed 좋아요 조회
    * */
    @GetMapping("/feed/heart/list")
    public ResponseVO<List<FeedHeartRVO>> feedHeartList(@RequestParam String feedNo) {
        log.info(this.getClass().getName() + " ==> Feed 좋아요 조회!");
        ResponseVO<List<FeedHeartRVO>> r = new ResponseVO<List<FeedHeartRVO>>();

        FeedHeartPVO pvo = new FeedHeartPVO();
        pvo.setFeedNo(Long.parseLong(feedNo));

        List<FeedHeartRVO> rvo = feedService.selectFeedHeartList(pvo).stream()
                .map(m -> m.feedHeartToRVO())
                .collect(Collectors.toList());

        r.setData(rvo);

        return r;
    }


    /*
    * Feed 댓글 작성
    * */
    @PostMapping("/user/feed/comment/regist")
    public ResponseVO<FeedCommentRVO> writeFeedComment(@RequestBody FeedCommentPVO pvo) {
        log.info(this.getClass().getName() + " ==> Feed 댓글 등록!");
        ResponseVO<FeedCommentRVO> r = new ResponseVO<FeedCommentRVO>();

        pvo.setId(CommonUtils.getUserId());

        FeedCommentRVO rvo = feedService.writeFeedComment(pvo)
                .feedCommentToRVO();
        
        rvo.setFeedCommentCnt(feedService.selectFeedCommnetCnt(pvo.getFeedNo()));   // 댓글 수

        r.setData(rvo);

        return r;
    }

    /*
    * Feed 댓글 조회
    * */
    @GetMapping("/feed/comment/list")
    public ResponseVO<List<FeedCommentRVO>> selectFeedCommentList(@RequestParam String feedNo) {
        log.info(this.getClass().getName() + " ==> Feed 댓글 조회");
        ResponseVO<List<FeedCommentRVO>> r = new ResponseVO<List<FeedCommentRVO>>();

        FeedCommentPVO pvo = new FeedCommentPVO();
        pvo.setFeedNo(Long.parseLong(feedNo));

        List<FeedCommentRVO> rvo = feedService.selectFeedCommentList(pvo);

        r.setData(rvo);

        return r;
    }

    /*
     * Feed 댓글 좋아요 기능
     * */
    @PostMapping("/user/feed/comment/heart")
    public ResponseVO<FeedCommentRVO> feedCommentHeart(@RequestBody FeedCommentPVO pvo) {
        log.info(this.getClass().getName() + " ==> Feed 댓글 좋아요!");
        ResponseVO<FeedCommentRVO> r = new ResponseVO<FeedCommentRVO>();

        pvo.setId(CommonUtils.getUserId());

        FeedCommentRVO rvo = feedService.feedCommentHeart(pvo)
                .feedCommentToRVO();

        r.setData(rvo);

        return r;
    }

    /*
    * Feed 보관
    * */
    @PostMapping("/user/feed/keep/save")
    public ResponseVO<FeedKeepRVO> feedKeepSave(@RequestBody FeedKeepPVO pvo) {
        log.info(this.getClass().getName() + " ==> Feed 보관!");
        ResponseVO<FeedKeepRVO> r = new ResponseVO<FeedKeepRVO>();

        pvo.setId(CommonUtils.getUserId());

        FeedKeepRVO rvo = feedService.feedKeepSave(pvo).feedKeepToRVO();

        r.setData(rvo);

        return r;
    }
}
