package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Feed.Repository.FeedDslRepository;
import com.api.upstagram.domain.MemberInfo.Repository.MemberInfoDslRepository;
import com.api.upstagram.vo.Search.SearchPVO;
import com.api.upstagram.vo.Search.SearchRVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {


    private final LoginService loginService;

    private final FeedService feedService;
    /*
    * 검색
    * */
    public List<?> selectSearchInfoList(SearchPVO pvo) {

        String searchDivisionCode = pvo.getSearchDivisionCode();
        List<?> list = new ArrayList<>();

        if("1".equals(searchDivisionCode) || "3".equals(searchDivisionCode)) list = loginService.selectMemberInfoSearchList(pvo); // 사용자 조회 & 태그 사용자 조회
        else if ("2".equals(searchDivisionCode)) list = feedService.selectFeedHashtagList(pvo); // 피드 조회 (해시태그)
        else throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());

        return list;
    }
}
