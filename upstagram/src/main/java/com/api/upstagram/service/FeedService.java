package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.FileInfo;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Feed.Entity.*;
import com.api.upstagram.domain.Feed.Repository.*;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.domain.MemberInfo.Repository.MemberInfoRepository;
import com.api.upstagram.vo.Feed.*;
import com.api.upstagram.vo.Search.SearchPVO;
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
import java.util.stream.Collectors;

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

    private final FeedKeepRepository feedKeepRepository;

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
        this.feedFileSaveAll(feed, files);

        // 3. Feed Tag 추가
        this.feedTagSaveAll(pvo, feed);

        return feed;
    }

    /*
    * Feed File 등록 & 업로드
    * */
    public void feedFileSaveAll(Feed feed, MultipartFile[] files) throws IOException{
        List<FeedFile> fileList = new ArrayList<FeedFile>();

        String[] exts = {"image/png", "image/jpg", "image/jpeg", "video/mp4", "video/avi"};
        int i = 1;

        for(MultipartFile file : files) {
            FileInfo fileInfo = CommonUtils.uploadFile(file, resourePath, exts);

            FeedFile feedFile = FeedFile.builder()
                    .feed(feed)
                    .fileName(fileInfo.getFileName())
                    .fileDiv("FEED")
                    .fileExt(fileInfo.getFileExt())
                    .sortOrder(i++)
                    .build();

            fileList.add(feedFile);
        }

        feedFileRepository.saveAll(fileList);
    }

    /*
    * Feed Tag 등록
    * */
    public void feedTagSaveAll(FeedPVO pvo, Feed feed) {
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

        if(feedTags.size() > 0)
            feedTagRepository.saveAll(feedTags);
    }

    /*
     * Feed List 조회
     * */
    public List<FeedRVO> selectFeedList(FeedPVO pvo){
        return feedRepository.selectFeedList(pvo.getId()).stream()
                .map(m -> FeedRVO.builder()
                        .feedNo(m.getFeedNo())
                        .title(m.getTitle())
                        .hashtag(m.getHashtag())
                        .useYn(m.getUseYn())
                        .id(m.getId())
                        .name(m.getName())
                        .nickname(m.getNickname())
                        .sex(m.getSex())
                        .tel(m.getTel())
                        .oauthNo(m.getOauthNo())
                        .feedHeartCnt(m.getFeedHeartCnt())
                        .feedCommentCnt(m.getFeedCommentCnt())
                        .feedFileNames(m.getFileNames())
                        .feedExts(m.getFileExts())
                        .feedKeepNo(m.getFeedKeepNo())
                        .feedHeartNo(m.getFeedHeartNo())
                        .build())
                .collect(Collectors.toList());
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
    * Feed 좋아요 조회
    * */
    public List<FeedHeart> selectFeedHeartList(FeedHeartPVO pvo) {
        return feedHeartRepository.selectFeedHeartList(pvo.getFeedNo());
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
     * Feed Comment 조회
     * */
    public List<FeedCommentRVO> selectFeedCommentList(FeedCommentPVO pvo) {
        return feedCommentRepository.selectFeedCommentList(pvo.getFeedNo()).stream()
                .map(m -> FeedCommentRVO.builder()
                        .feedCommentNo(m.getFeedCommentNo())
                        .feedNo(m.getFeedNo())
                        .id(m.getId())
                        .name(m.getName())
                        .nickname(m.getNickname())
                        .sex(m.getSex())
                        .tel(m.getTel())
                        .oauthNo(m.getOauthNo())
                        .content(m.getContent())
                        .useYn(m.getUseYn())
                        .topFix(m.getTopFix())
                        .heartCnt(m.getHeartCnt())
                        .build()
                )
                .collect(Collectors.toList());
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
    * Feed 보관
    * */
    public FeedKeep feedKeepSave(FeedKeepPVO pvo) {
        Feed feed = Feed.builder()
                .feedNo(pvo.getFeedNo())
                .build();

        MemberInfo memberInfo = MemberInfo.builder()
                .id(pvo.getId())
                .build();

        Optional<FeedKeep> feedKeepOpt = feedKeepRepository.findByFeedAndMember(feed, memberInfo);

        if(feedKeepOpt.isPresent()) {
            FeedKeep feedKeep = feedKeepOpt.get();
            feedKeepRepository.delete(feedKeep);
            return feedKeep;
        } else {
            return feedKeepRepository.save(FeedKeep.builder()
                    .feed(feed)
                    .member(memberInfo)
                    .build());
        }
    }

    /*
    * Feed 보관내역 조회
    * */
    public List<FeedRVO> selectFeedKeepList(FeedPVO pvo) {
        return feedKeepRepository.selectFeedKeepList(pvo.getId()).stream()
                .map(m -> FeedRVO.builder()
                        .feedNo(m.getFeedNo())
                        .title(m.getTitle())
                        .hashtag(m.getHashtag())
                        .useYn(m.getUseYn())
                        .id(m.getId())
                        .name(m.getName())
                        .nickname(m.getNickname())
                        .sex(m.getSex())
                        .tel(m.getTel())
                        .oauthNo(m.getOauthNo())
                        .feedHeartCnt(m.getFeedHeartCnt())
                        .feedCommentCnt(m.getFeedCommentCnt())
                        .feedFileNames(m.getFileNames())
                        .build())
                .collect(Collectors.toList());
    }

    /*
    * Feed Hashtag 조회
    * */
    public List<String> selectSearchHashtagList(SearchPVO pvo){
        List<String> list = feedRepository.selectSearchHashtagList(pvo.getSearchValue());
        return null;
    }
}
