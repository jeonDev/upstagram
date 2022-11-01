package com.api.upstagram.service;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.FileInfo;
import com.api.upstagram.domain.Feed.Feed;
import com.api.upstagram.domain.Feed.FeedFile;
import com.api.upstagram.domain.Feed.FeedFileRepository;
import com.api.upstagram.domain.Feed.FeedRepository;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Feed.FeedPVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedRepository feedRepository;

    private final FeedFileRepository feedFileRepository;

    @Value("${resource-path}")
    private String resourePath;

    /*
    * Feed 등록
    * */
    @Transactional
    public Feed insertFeed(FeedPVO pvo, MultipartFile[] files) throws IOException {

        MemberInfo memberInfo = MemberInfo.builder()
                .id(pvo.getId())
                .build();

        // 1. Feed 등록
        Feed feed = Feed.builder()
                .member(memberInfo)
                .title(pvo.getTitle())
                .hashtag(pvo.getHashtag())
                .useYn(pvo.getUseYn() != null ? pvo.getUseYn() : "Y")
                .build();

        feed = feedRepository.save(feed);

        // 2. Feed File 업로드 & 등록
        List<FeedFile> fileList = new ArrayList<FeedFile>();

        String[] exts = {"image/png", "image/jpg", "image/jpeg", "video/mp4", "video/avi"};
        int i = 1;

        for(MultipartFile file : files) {
            FileInfo fileInfo = CommonUtils.uploadFile(file, resourePath, exts);

            FeedFile feedFile = FeedFile.builder()
                    .feed(feed)
                    .fileDiv("FEED")
                    .fileExt(fileInfo.getFileExt())
                    .sortOrder(i++)
                    .build();

            fileList.add(feedFile);
        }

        feedFileRepository.saveAll(fileList);

        return feed;
    }
}
