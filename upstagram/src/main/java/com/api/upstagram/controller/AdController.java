package com.api.upstagram.controller;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.Ad.Ad;
import com.api.upstagram.domain.Ad.AdCompany;
import com.api.upstagram.service.AdService;
import com.api.upstagram.vo.Ad.AdCompanyPVO;
import com.api.upstagram.vo.Ad.AdCompanyRVO;
import com.api.upstagram.vo.Ad.AdPVO;
import com.api.upstagram.vo.Ad.AdRVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping("/ad/regist")
    public ResponseVO<AdRVO> adRegister(@RequestPart AdPVO pvo, @RequestPart MultipartFile file) throws IOException {
        ResponseVO<AdRVO> r = new ResponseVO<AdRVO>();

        Ad ad = adService.adRegister(pvo, file);
        AdRVO rvo = AdRVO.builder()
                .adNo(ad.getAdNo())
                .adCompanyNo(ad.getAdCompany().getAdCompanyNo())
                .adCompanyName(ad.getAdCompany().getAdCompanyName())
                .adName(ad.getAdName())
                .adFileName(ad.getAdFileName())
                .useYn(ad.getUseYn())
                .fileDiv(ad.getFileDiv())
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
}
