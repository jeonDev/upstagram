package com.api.upstagram.controller;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.service.SearchService;
import com.api.upstagram.vo.Search.SearchPVO;
import com.api.upstagram.vo.Search.SearchRVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
    * 검색
    * searchDivisionCode : 1 (사용자조회 : SearchRVO) / 2 : (피드 해시태그조회 : FeedRVO)
    * */
    @GetMapping("/user/search")
    public ResponseVO<List<?>> selectSearchInfoList(@RequestParam("searchDivisionCode") String searchDivisionCode
            , @RequestParam(value = "searchValue", required = false) String searchValue) {
        log.info(this.getClass().getName() + " ==> 검색정보 조회");
        ResponseVO<List<?>> r = new ResponseVO<List<?>>();

        SearchPVO pvo = new SearchPVO();
        pvo.setSearchDivisionCode(searchDivisionCode);
        pvo.setSearchValue(searchValue);
        pvo.setId(CommonUtils.getUserId());

        List<?> rvo = searchService.selectSearchInfoList(pvo);
        
        r.setData(rvo);

        return r;
    }
}
