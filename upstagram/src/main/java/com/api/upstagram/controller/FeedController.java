package com.api.upstagram.controller;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.Feed.Feed;
import com.api.upstagram.service.FeedService;
import com.api.upstagram.vo.Feed.FeedPVO;
import com.api.upstagram.vo.Feed.FeedRVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
}
