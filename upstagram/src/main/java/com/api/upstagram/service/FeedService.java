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

    private final FeedDslRepository feedDslRepository;

    private final FeedHashtagRepository feedHashtagRepository;

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
                .useYn(!StringUtils.isNotEmpty(pvo.getUseYn()) ? pvo.getUseYn() : "Y")
                .build();

        feed = feedRepository.save(feed);

        // 2. Feed File 업로드 & 등록
        this.feedFileSaveAll(feed, files);

        // 3. Feed Tag 추가
        this.feedTagSaveAll(pvo, feed);

        // 4. Feed Hashtag 추가
        this.feedHashtagSaveAll(pvo, feed);

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
        
        // TODO: 태그 아이디 알림
    }

    /*
    * Feed Hashtag 등록
    * */
    public void feedHashtagSaveAll(FeedPVO pvo, Feed feed) {
        // 등록된 해시태그가 없을 경우 return
        if(pvo.getHashtag() == null || pvo.getHashtag().size() == 0) return;

        List<FeedHashtag> hashtagList = new ArrayList<FeedHashtag>();

        for(String hashtag : pvo.getHashtag()) {

            if(StringUtils.isNotEmpty(hashtag)) continue;

            FeedHashtag feedHashtag = FeedHashtag.builder()
                    .feed(feed)
                    .hashtag(hashtag.trim())
                    .build();

            hashtagList.add(feedHashtag);
        }

        if(hashtagList.size() > 0)
            feedHashtagRepository.saveAll(hashtagList);
    }

    /*
     * Feed List 조회
     * FeedDivisionCode
     *  1 : 팔로우 Feed
     *      - loginId
     *  2 : Keep Feed
     *      - keepYn : Y
     *  3 : User Feed
     *      - writerId
     *  4 : Tag Feed
     *      - tagId
     *      - loginId => writerId
     *  5 : Hashtag Feed
     *      - hashtag
     * */
    public List<FeedRVO> selectFeedList(FeedListPVO pvo){

        if(StringUtils.isNotEmpty(pvo.getFeedDivisionCode())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());

        List<FeedRVO> list = new ArrayList<>();
        switch (pvo.getFeedDivisionCode()) {
            case "1" : // Follow Feed
                list = feedDslRepository.selectFollowFeedList(pvo);
                break;
            case "2" :
                pvo.setFeedKeepYn("Y");
                list = feedDslRepository.selectFeedList(pvo);
                break;
            case "3" : // User Feed
                if(StringUtils.isNotEmpty(pvo.getWriterId())) pvo.setWriterId(pvo.getId());
                list = feedDslRepository.selectFeedList(pvo);
                break;
            case "4" : // Tag Feed
                if(StringUtils.isNotEmpty(pvo.getTagId())) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());
                pvo.setWriterId(pvo.getId());
                list = feedDslRepository.selectFeedList(pvo);
                break;
            case "5" :
                break;
            case "6" :
                pvo.setFeedHeartYn("Y");
                list = feedDslRepository.selectFeedList(pvo);
                break;
            default:
                throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());
        }


        return list;
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
                    Feed.builder()
                        .feedNo(pvo.getFeedNo())
                        .build(),
                    MemberInfo.builder()
                        .id(pvo.getId())
                        .build()
        );

        if(!feedHeart.isPresent()) {
            return feedHeartRepository.save(FeedHeart.builder()
                            .feed(Feed.builder().feedNo(pvo.getFeedNo()).build())
                            .member(MemberInfo.builder().id(pvo.getId()).build())
                            .build());
        } else {
            FeedHeart deleteFeedHeart = feedHeart.get();
            feedHeartRepository.delete(deleteFeedHeart);
            return null;
        }
    }

    /**
     * Feed 좋아요 갯수 조회
     * */
    public int selectFeedHeartCnt(Long feedNo) {
        return feedHeartRepository.selectFeedHeartCnt(feedNo);
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
    
    /**
     * Feed 댓글 개수 조회
     * */
    public int selectFeedCommnetCnt(Long feedNo) {
        return feedCommentRepository.selectFeedCommnetCnt(feedNo);
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

    /**
     * Hashtag 검색
     * */
    public List<FeedHashtagRVO> selectFeedHashtagList(SearchPVO pvo) {
        return feedDslRepository.selectSearchHashtagList(pvo);
    }

    /**
     * Feed Tag List 조회
     * */
    public List<FeedTagRVO> selectFeedTagList(Long feedNo) {
        return feedTagRepository.selectFeedTagList(feedNo).stream()
                .map(m -> FeedTagRVO.builder()
                        .feedTagNo(m.getFeedTagNo())
                        .feedNo(m.getFeed().getFeedNo())
                        .id(m.getMember().getId())
                        .name(m.getMember().getName())
                        .nickname(m.getMember().getNickname())
                        .oauthNo(m.getMember().getOauthNo())
                        .tel(m.getMember().getTel())
                        .sex(m.getMember().getSex())
                        .build())
                .collect(Collectors.toList());
    }
}
