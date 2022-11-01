package com.api.upstagram.service;

import com.api.upstagram.domain.Feed.Feed;
import com.api.upstagram.domain.Feed.FeedRepository;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Feed.FeedPVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedRepository feedRepository;

    /*
    * Feed 등록
    * */
    public Feed insertFeed(FeedPVO pvo, MultipartFile[] files) throws IOException {



        MemberInfo memberInfo = MemberInfo.builder()
                .id(pvo.getId())
                .build();

        Feed feed = Feed.builder()
                .member(memberInfo)
                .title(pvo.getTitle())
                .hashtag(pvo.getHashtag())
                .useYn(pvo.getUseYn() != null ? pvo.getUseYn() : "Y")
                .build();

        return feedRepository.save(feed);
    }
}
