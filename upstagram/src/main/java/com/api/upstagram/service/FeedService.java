package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.FileInfo;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Feed.*;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.domain.MemberInfo.MemberInfoRepository;
import com.api.upstagram.vo.Feed.FeedCommentPVO;
import com.api.upstagram.vo.Feed.FeedHeartPVO;
import com.api.upstagram.vo.Feed.FeedPVO;
import com.api.upstagram.vo.Feed.FeedRVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedRepository feedRepository;

    private final FeedFileRepository feedFileRepository;

    private final FeedHeartRepository feedHeartRepository;

    private final FeedTagRepository feedTagRepository;

    private final FeedCommentRepository feedCommentRepository;

    private final FeedCommentHeartRepository feedCommentHeartRepository;

    private final MemberInfoRepository memberInfoRepository;

    @Value("${resource-path}")
    private String resourePath;

    /*
    * Feed 등록
    * */
    @Transactional
    public Feed insertFeed(FeedPVO pvo, MultipartFile[] files) throws IOException {
        log.info(this.getClass().getName() + " ==> Feed Upload!");
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

        // 3. Feed Tag 추가
        List<String> tagIdList = pvo.getTagId();
        List<FeedTag> feedTags = new ArrayList<FeedTag>();
        for(String tagId : tagIdList) {
            Optional<MemberInfo> member = memberInfoRepository.findByIdAndUseYn(tagId, "Y");

            if(!member.isPresent()) continue;
            
            FeedTag feedTag = FeedTag.builder()
                    .feed(feed)
                    .member(member.get())
                    .build();

            feedTags.add(feedTag);
        }

        if(feedTags.size() > 0) feedTagRepository.saveAll(feedTags);

        return feed;
    }

    /*
    * Feed 수정
    * */
    @Transactional
    public Feed updateFeed(FeedPVO pvo, MultipartFile[] files) throws IOException{
        return null;
    }

    /*
    * Feed 좋아요 기능
    * */
    public FeedHeart feedHeartSave(FeedHeartPVO pvo) {

        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "로그인 후에 이용해주세요.");

        Optional<FeedHeart> feedHeart = feedHeartRepository.findByFeedAndMember(
                Feed.builder().feedNo(pvo.getFeedNo()).build(), MemberInfo.builder().id(pvo.getId()).build());

        if(!feedHeart.isPresent()) {
            return feedHeartRepository.save(FeedHeart.builder()
                            .feed(Feed.builder().feedNo(pvo.getFeedNo()).build())
                            .member(MemberInfo.builder().id(pvo.getId()).build())
                            .build());
        } else {
            FeedHeart deleteFeedHeart = feedHeart.get();
            feedHeartRepository.delete(deleteFeedHeart);
            return deleteFeedHeart;
        }
    }

    /*
     * Feed 댓글 기능
     */
    public FeedComment writeFeedComment(FeedCommentPVO pvo) {

        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "로그인 후에 이용해주세요.");

        Optional<Feed> feed = feedRepository.findById(pvo.getFeedNo());
        if(!feed.isPresent()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "게시글이 존재하지 않습니다.");

        FeedComment feedComment = FeedComment.builder()
                .feedCommentNo(pvo.getFeedCommentNo())
                .feed(feed.get())
                .member(MemberInfo.builder().id(pvo.getId()).build())
                .useYn("Y")
                .topFix("N")
                .content(pvo.getContent())
                .build();

        return feedCommentRepository.save(feedComment);
    }

    /*
    * Feed 댓글 좋아요 기능
    * */
    public FeedCommentHeart feedCommentHeart(FeedCommentPVO pvo) {

        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), "로그인 후에 이용해주세요.");

        Optional<FeedCommentHeart> feedCommentHeart = feedCommentHeartRepository.findByFeedCommentAndMember(
                FeedComment.builder().feedCommentNo(pvo.getFeedCommentNo()).build()
                , MemberInfo.builder().id(pvo.getId()).build());

        if(!feedCommentHeart.isPresent()) {
            return feedCommentHeartRepository.save(FeedCommentHeart.builder()
                            .feedComment(FeedComment.builder().feedCommentNo(pvo.getFeedCommentNo()).build())
                            .member(MemberInfo.builder().id(pvo.getId()).build())
                            .build());
        } else {
            FeedCommentHeart deleteFeedCommentHeart = feedCommentHeart.get();
            feedCommentHeartRepository.delete(deleteFeedCommentHeart);
            return deleteFeedCommentHeart;
        }
    }

    /*
    * Feed List 조회
    * */
    public List<Feed> selectFeedList(FeedPVO pvo){
        return feedRepository.selectFeedList(pvo.getId());
    }
}
