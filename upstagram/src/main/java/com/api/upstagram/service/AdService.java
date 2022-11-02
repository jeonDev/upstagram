package com.api.upstagram.service;

import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.domain.Ad.AdCompany;
import com.api.upstagram.domain.Ad.AdCompanyRepository;
import com.api.upstagram.domain.Ad.AdRepository;
import com.api.upstagram.vo.Ad.AdCompanyPVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    private final AdCompanyRepository adCompanyRepository;


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
}
