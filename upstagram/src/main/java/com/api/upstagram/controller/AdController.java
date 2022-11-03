package com.api.upstagram.controller;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.Ad.Ad;
import com.api.upstagram.domain.Ad.AdCompany;
import com.api.upstagram.domain.Ad.AdViewHistory;
import com.api.upstagram.service.AdService;
import com.api.upstagram.vo.Ad.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class AdController {

    @Autowired
    private AdService adService;

    /*
    * 광고회사 등록
    * */
    @PostMapping("/admin/ad/company/regist")
    public ResponseVO<AdCompanyRVO> adCompanyRegister(@RequestBody AdCompanyPVO pvo) {
        log.info(this.getClass().getName() + " ==> 광고 회사 추가 및 수정!");
        ResponseVO<AdCompanyRVO> r = new ResponseVO<AdCompanyRVO>();

        AdCompany adCompany = adService.adCompanyRegister(pvo);
        AdCompanyRVO rvo = AdCompanyRVO.builder()
                .adCompanyNo(adCompany.getAdCompanyNo())
                .adCompanyName(adCompany.getAdCompanyName())
                .build();

        r.setData(rvo);

        return r;
    }

    /*
    * 광고 등록
    * */
    @PostMapping("/manage/ad/regist")
    public ResponseVO<AdRVO> adRegister(@RequestPart AdPVO pvo, @RequestPart MultipartFile file) throws IOException {
        log.info(this.getClass().getName() + " ==> 광고 등록!");
        ResponseVO<AdRVO> r = new ResponseVO<AdRVO>();

        Ad ad = adService.adRegister(pvo, file);
        AdRVO rvo = AdRVO.builder()
                .adNo(ad.getAdNo())
                .adCompanyNo(ad.getAdCompany().getAdCompanyNo())
                .adCompanyName(ad.getAdCompany().getAdCompanyName())
                .adName(ad.getAdName())
                .adFileName(ad.getAdFileName())
                .useYn(ad.getUseYn())
                .fileExt(ad.getFileExt())
                .costDiv(ad.getCostDiv())
                .timeCountCost(ad.getTimeCountCost())
                .viewCountCost(ad.getViewCountCost())
                .linkCountCost(ad.getLinkCountCost())
                .link(ad.getLink())
                .build();

        r.setData(rvo);

        return r;
    }

    /*
    * 광고 시청기록 등록
    * */
    @GetMapping("/ad/view")
    public ResponseVO<AdViewHistoryRVO> adViewHistory(@RequestParam String adNo, @RequestParam String link, @RequestParam String linkCountYn) {
        log.info(this.getClass().getName() + " ==> 광고 시청 기록 등록!");
        ResponseVO<AdViewHistoryRVO> r = new ResponseVO<AdViewHistoryRVO>();

        AdViewHistoryPVO pvo = new AdViewHistoryPVO();
        pvo.setId(CommonUtils.getUserId());
        pvo.setAdNo(Long.parseLong(adNo));
        pvo.setLink(link);
        pvo.setLinkCountYn(linkCountYn);

        AdViewHistory adViewHistory = adService.adViewHistory(pvo);
        AdViewHistoryRVO rvo = AdViewHistoryRVO.builder()
                .adViewNo(adViewHistory.getAdViewNo())
                .adNo(adViewHistory.getAd().getAdNo())
                .id(adViewHistory.getMember().getId())
                .linkCountYn(adViewHistory.getLinkCountYn())
                .link(adViewHistory.getLink())
                .viewDttm(adViewHistory.getViewDttm())
                .build();

        r.setData(rvo);

        return r;
    }
}
