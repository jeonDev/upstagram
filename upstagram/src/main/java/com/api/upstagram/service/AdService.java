package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.FileInfo;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Ad.Ad;
import com.api.upstagram.domain.Ad.AdCompany;
import com.api.upstagram.domain.Ad.AdCompanyRepository;
import com.api.upstagram.domain.Ad.AdRepository;
import com.api.upstagram.vo.Ad.AdCompanyPVO;
import com.api.upstagram.vo.Ad.AdPVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    private final AdCompanyRepository adCompanyRepository;

    @Value("${resource-path}")
    private String resourePath;

    /*
    * 광고회사 등록
    * */
    public AdCompany adCompanyRegister(AdCompanyPVO pvo) {

        return this.adCompanySave(pvo);
    }

    public AdCompany adCompanySave(AdCompanyPVO pvo) {
        return adCompanyRepository.save(AdCompany.builder()
                        .adCompanyNo(pvo.getAdCompanyNo())
                        .adCompanyName(pvo.getAdCompanyName())
                        .build());
    }

    /*
    * 광고내역 등록
    * */
    public Ad adRegister(AdPVO pvo, MultipartFile file) throws IOException {


        Optional<AdCompany> adCompany = adCompanyRepository.findById(pvo.getAdCompanyNo());
        if(!adCompany.isPresent()) throw new CustomException(Response.ARGUMNET_ERROR.getCode(), Response.ARGUMNET_ERROR.getMessage());

        String[] exts = {"image/png", "image/jpg", "image/jpeg", "video/mp4", "video/avi"};
        FileInfo fileInfo = CommonUtils.uploadFile(file, resourePath, exts);

        Ad ad = Ad.builder()
                .adCompany(adCompany.get())
                .adName(pvo.getAdName())
                .useYn("Y")
                .costDiv(pvo.getCostDiv())
                .timeCountCost(pvo.getTimeCountCost())
                .viewCountCost(pvo.getViewCountCost())
                .linkCountCost(pvo.getLinkCountCost())
                .linkUseYn(pvo.getLinkUseYn())
                .link(pvo.getLink())
                .adFileName(fileInfo.getFileName())
                .fileDiv("")
                .fileExt(fileInfo.getFileExt())
                .build();

        return adRepository.save(ad);
    }
}
