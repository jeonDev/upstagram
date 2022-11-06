package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.FileInfo;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Ad.*;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Ad.AdCompanyPVO;
import com.api.upstagram.vo.Ad.AdPVO;
import com.api.upstagram.vo.Ad.AdViewHistoryPVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    private final AdCompanyRepository adCompanyRepository;

    private final AdViewHistoryRepository adViewHistoryRepository;

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
}
