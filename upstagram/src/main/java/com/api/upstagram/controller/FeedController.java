package com.api.upstagram.controller;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.Feed.Feed;
import com.api.upstagram.domain.Feed.FeedComment;
import com.api.upstagram.domain.Feed.FeedCommentHeart;
import com.api.upstagram.domain.Feed.FeedHeart;
import com.api.upstagram.service.FeedService;
import com.api.upstagram.vo.Feed.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

        Feed feed = feedService.insertFeed(pvo, files);
        FeedRVO rvo = FeedRVO.builder()
                .feedNo(feed.getFeedNo())
                .title(feed.getTitle())
                .hashtag(feed.getHashtag())
                .useYn(feed.getUseYn())
                .id(feed.getMember().getId())
                .build();

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
    @PostMapping("/user/feed/heart")
    public ResponseVO<FeedHeartRVO> feedHeart(@RequestBody FeedHeartPVO pvo) {
        log.info(this.getClass().getName() + " ==> Feed 좋아요!");
        ResponseVO<FeedHeartRVO> r = new ResponseVO<FeedHeartRVO>();

        pvo.setId(CommonUtils.getUserId());

        FeedHeart feedHeart = feedService.feedHeartSave(pvo);
        FeedHeartRVO rvo = FeedHeartRVO.builder()
                .feedHeartNo(feedHeart.getFeedHeartNo())
                .feedNo(feedHeart.getFeed().getFeedNo())
                .id(feedHeart.getMember().getId())
                .build();

        r.setData(rvo);

        return r;
    }

    /*
    * Feed 댓글 작성
    * */
    @PostMapping("/user/feed/comment")
    public ResponseVO<FeedCommentRVO> writeFeedComment(@RequestBody FeedCommentPVO pvo) {
        log.info(this.getClass().getName() + " ==> Feed 댓글 등록!");
        ResponseVO<FeedCommentRVO> r = new ResponseVO<FeedCommentRVO>();

        pvo.setId(CommonUtils.getUserId());

        FeedComment feedComment = feedService.writeFeedComment(pvo);
        FeedCommentRVO rvo = FeedCommentRVO.builder()
                .feedCommentNo(feedComment.getFeedCommentNo())
                .feedNo(feedComment.getFeed().getFeedNo())
                .id(feedComment.getMember().getId())
                .content(feedComment.getContent())
                .topFix(feedComment.getTopFix())
                .useYn(feedComment.getUseYn())
                .build();

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

        FeedCommentHeart feedCommentHeart = feedService.feedCommentHeart(pvo);
        FeedCommentRVO rvo = FeedCommentRVO.builder()
                .feedCommentHeartNo(feedCommentHeart.getFeedCommentHeartNo())
                .commentHeartId(feedCommentHeart.getMember().getId())
                .feedCommentNo(feedCommentHeart.getFeedComment().getFeedCommentNo())
                .build();

        r.setData(rvo);

        return r;
    }
}
