package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Feed.Repository.FeedDslRepository;
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

    private final FeedDslRepository feedDslRepository;
    /*
    * 검색
    * */
    public List<?> selectSearchInfoList(SearchPVO pvo) {

        String searchDivisionCode = pvo.getSearchDivisionCode();
        List<?> list = new ArrayList<>();

        // 사용자 조회
        if("1".equals(searchDivisionCode)) {
            list = loginService.selectMemberInfoSearchList(pvo).stream()
                    .map(m -> SearchRVO.builder()
                            .id(m.getId())
                            .name(m.getName())
                            .nickname(m.getNickname())
                            .sex(m.getSex())
                            .tel(m.getTel())
                            .profileImage((m.getProfileImage()))
                            .build()
                    )
                    .collect(Collectors.toList());
        // 피드 조회 (해시태그)
        } else if ("2".equals(searchDivisionCode)) {
            list = feedDslRepository.selectSearchFeedList(pvo);
        } else {
            throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());
        }

        return list;
    }
}
