package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.FileInfo;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Ad.*;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Ad.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    private final AdCompanyRepository adCompanyRepository;

    private final AdViewHistoryRepository adViewHistoryRepository;

    private final AdManageRepository adManageRepository;

    @Value("${resource-path}")
    private String resourePath;

    /*
    * 광고회사 등록
    * */
    public AdCompany adCompanyRegister(AdCompanyPVO pvo) {
        log.info(this.getClass().getName() + " ==> 광고 회사 Save!");
        return adCompanyRepository.save(AdCompany.builder()
                .adCompanyNo(pvo.getAdCompanyNo())
                .adCompanyName(pvo.getAdCompanyName())
                .managerName(pvo.getManagerName())
                .managerTel(pvo.getManagerTel())
                .addr(pvo.getAddr())
                .addrDetail(pvo.getAddrDetail())
                .zipCd(pvo.getZipCd())
                .build());
    }

    /*
    * 광고회사 조회
    * */
    public List<AdCompany> selectAdCompanyList() {
        return adCompanyRepository.selectAdCompanyList();
    }

    /*
    * 광고내역 등록
    * */
    public Ad adRegister(AdPVO pvo, MultipartFile file) throws IOException {
        log.info(this.getClass().getName() + " ==> 광고 내역 등록!");

        Optional<AdCompany> adCompany = adCompanyRepository.findById(pvo.getAdCompanyNo());
        if(!adCompany.isPresent()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());

        String[] exts = {"image/png", "image/jpg", "image/jpeg", "video/mp4", "video/avi"};
        FileInfo fileInfo = CommonUtils.uploadFile(file, resourePath, exts);

        Ad ad = Ad.builder()
                .adCompany(adCompany.get())
                .adName(pvo.getAdName())
                .useYn(pvo.getUseYn() != null ? pvo.getUseYn() : "Y")
                .costDiv(pvo.getCostDiv())
                .timeCountCost(pvo.getTimeCountCost())
                .viewCountCost(pvo.getViewCountCost())
                .linkCountCost(pvo.getLinkCountCost())
                .linkUseYn(pvo.getLinkUseYn())
                .link(pvo.getLink())
                .adFileName(fileInfo.getFileName())
                .fileExt(fileInfo.getFileExt())
                .build();

        return adRepository.save(ad);
    }

    /*
    * 광고 조회
    * */
    public List<Ad> selectAdList() {
        return adRepository.selectAdList(CommonUtils.format(new Date(), "yyyyMMdd")
                , CommonUtils.format(new Date(), "HHmmss"));
    }

    /*
    * 광고 조회 (Admin - 전체)
    * */
    public List<Ad> selectAdminAdList() {
        return adRepository.selectAllAdList();
    }

    /*
    * 광고 시청내역 등록
    * */
    public AdViewHistory adViewHistory(AdViewHistoryPVO pvo) {
        log.info(this.getClass().getName() + " ==> 광고 시청내역 등록!");

        return adViewHistoryRepository.save(AdViewHistory.builder()
                    .ad(Ad.builder().adNo(pvo.getAdNo()).build())
                    .member(MemberInfo.builder().id(pvo.getId()).build())
                    .link(pvo.getLink())
                    .linkCountYn(pvo.getLinkCountYn())
                    .viewDttm(LocalDateTime.now())
                    .build());
    }

    /*
    * 광고 시청내역 조회
    * */
    public List<AdViewHistory> selectAdViewHistoryList(AdViewHistoryPVO pvo) {
        return adViewHistoryRepository.selectAdViewHistoryList(pvo.getAdNo());
    }

    /*
    * 광고 노출시간 등록 & 수정
    * */
    public AdManage adManageSave(AdManagePVO pvo) {

        Ad ad = Ad.builder()
                .adNo(pvo.getAdNo())
                .build();

        // TODO: 입력한 날짜 & 시간 중복되는지 체크.

        Optional<AdManage> adManageOpt = adManageRepository.findByAd(ad);

        if(adManageOpt.isPresent()) {
            AdManage adManage = adManageOpt.get();

            if(adManage.getAdManageNo() == null || adManage.getAdManageNo() == 0L) throw new CustomException(Response.DUPLICATION_ERROR.getCode(), Response.DUPLICATION_ERROR.getMessage());

            return adManageRepository.save(AdManage.builder()
                    .adManageNo(adManage.getAdManageNo())
                    .ad(ad)
                    .startDttm(pvo.getStartDttm())
                    .endDttm(pvo.getEndDttm())
                    .startTime(pvo.getStartTime())
                    .endTime(pvo.getEndTime())
                    .build());
        } else {
            return adManageRepository.save(AdManage.builder()
                    .ad(ad)
                    .startDttm(pvo.getStartDttm())
                    .endDttm(pvo.getEndDttm())
                    .startTime(pvo.getStartTime())
                    .endTime(pvo.getEndTime())
                    .build());
        }
    }

    /*
    * 광고별 노출시간 조회
    * */
    public List<AdManage> adManageList() {
        return adManageRepository.selectAdManageList();
    }
}
