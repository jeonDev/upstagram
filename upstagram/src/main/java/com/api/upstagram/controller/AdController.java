package com.api.upstagram.controller;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.Ad.AdCompany;
import com.api.upstagram.service.AdService;
import com.api.upstagram.vo.Ad.AdCompanyPVO;
import com.api.upstagram.vo.Ad.AdCompanyRVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
