package com.api.upstagram.controller;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.service.AdService;
import com.api.upstagram.vo.Ad.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

        AdCompanyRVO rvo = adService.adCompanyRegister(pvo).adCompanyToRVO();

        r.setData(rvo);

        return r;
    }

    /*
    * 광고회사 조회
    * */
    @GetMapping("/admin/ad/company")
    public ResponseVO<List<AdCompanyRVO>> adCompanyList() {
        log.info(this.getClass().getName() + " ==> 광고 회사 조회");
        ResponseVO<List<AdCompanyRVO>> r = new ResponseVO<List<AdCompanyRVO>>();

        List<AdCompanyRVO> rvo = adService.selectAdCompanyList().stream()
                .map(m -> m.adCompanyToRVO())
                .collect(Collectors.toList());

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

        AdRVO rvo = adService.adRegister(pvo, file).adToRVO();

        r.setData(rvo);

        return r;
    }

    /*
     * 광고 조회
     * */
    @GetMapping("/ad/list")
    public ResponseVO<List<AdRVO>> selectAdList() {
        log.info(this.getClass().getName() + " ==> 광고 조회");
        ResponseVO<List<AdRVO>> r = new ResponseVO<List<AdRVO>>();

        List<AdRVO> rvo = adService.selectAdList().stream()
                .map(m -> m.adToRVO())
                .collect(Collectors.toList());

        r.setData(rvo);

        return r;
    }

    /*
    * 광고 조회 (admin - 전체)
    * */
    @GetMapping("/admin/ad/list")
    public ResponseVO<List<AdRVO>> selectAdminAdList() {
        log.info(this.getClass().getName() + " ==> 광고 조회");
        ResponseVO<List<AdRVO>> r = new ResponseVO<List<AdRVO>>();

        List<AdRVO> rvo = adService.selectAdminAdList().stream()
                .map(m -> m.adToRVO())
                .collect(Collectors.toList());

        r.setData(rvo);

        return r;
    }

    /*
    * 광고 시청기록 등록
    * */
    @PostMapping("/ad/view")
    public ResponseVO<AdViewHistoryRVO> adViewHistory(@RequestBody AdViewHistoryPVO pvo) {
        log.info(this.getClass().getName() + " ==> 광고 시청 기록 등록!");
        ResponseVO<AdViewHistoryRVO> r = new ResponseVO<AdViewHistoryRVO>();

        AdViewHistoryRVO rvo = adService.adViewHistory(pvo).adViewHistoryToRVO();

        r.setData(rvo);

        return r;
    }

    /*
    * 광고 시청기록 조회
    * */
    @GetMapping("/ad/view/list")
    public ResponseVO<List<AdViewHistoryRVO>> selectAdViewHistoryList(@RequestParam(required = false) String adNo) {
      log.info(this.getClass().getName() + " ==> 광고 시청 기록 조회");
      ResponseVO<List<AdViewHistoryRVO>> r = new ResponseVO<List<AdViewHistoryRVO>>();

      AdViewHistoryPVO pvo = new AdViewHistoryPVO();
      pvo.setAdNo(Long.parseLong(adNo));

      List<AdViewHistoryRVO> rvo = adService.selectAdViewHistoryList(pvo).stream()
              .map(m -> m.adViewHistoryToRVO())
              .collect(Collectors.toList());

      r.setData(rvo);

      return r;
    }

    /*
    * 광고 노출시간 등록
    * */
    @PostMapping("/admin/ad/manage/save")
    public ResponseVO<AdManageRVO> adManageSave(@RequestBody AdManagePVO pvo) {
        log.info(this.getClass().getName() + " ==> 광고 노출시간 등록");
        ResponseVO<AdManageRVO> r = new ResponseVO<AdManageRVO>();

        AdManageRVO rvo = adService.adManageSave(pvo).adManageToRVO();

        r.setData(rvo);

        return r;
    }

    /*
    * 광고별 광고 노출시간 조회
    * */
    @GetMapping("/admin/ad/manage/list")
    public ResponseVO<List<AdManageRVO>> adManageList() {
        log.info(this.getClass().getName() + " ==> 광고별 노출시간 조회");
        ResponseVO<List<AdManageRVO>> r = new ResponseVO<List<AdManageRVO>>();

        List<AdManageRVO> rvo = adService.adManageList().stream()
                .map(m -> m.adManageToRVO())
                .collect(Collectors.toList());

        r.setData(rvo);

        return r;
    }
}
