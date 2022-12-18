package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.CommonCode.Entity.CommonCode;
import com.api.upstagram.domain.CommonCode.Entity.CommonCodeId;
import com.api.upstagram.domain.CommonCode.Repository.CommonCodeRepository;
import com.api.upstagram.vo.CommonCode.CommonCodePVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonCodeRepository commonCodeRepository;

    /*
    * 공통코드 등록
    * */
    public CommonCode commonCodeRegister(CommonCodePVO pvo) {

        CommonCodeId commonCodeId = CommonCodeId.builder()
                .commonCode(pvo.getCommonCode())
                .commonType(pvo.getCommonType())
                .build();

        Optional<CommonCode> commonCode = commonCodeRepository.findById(commonCodeId);

        if(commonCode.isPresent()) throw new CustomException(Response.DUPLICATION_ERROR.getCode(), Response.DUPLICATION_ERROR.getMessage());

        return this.commonCodeSave(pvo);
    }

    /*
    * 공통코드 수정
    * */
    public CommonCode commonCodeUpdate(CommonCodePVO pvo) {
        return this.commonCodeSave(pvo);
    }

    public CommonCode commonCodeSave(CommonCodePVO pvo) {
        return commonCodeRepository.save(CommonCode.builder()
                .commonCode(pvo.getCommonCode())
                .commonCodeName(pvo.getCommonCodeName())
                .commonType(pvo.getCommonType())
                .useYn(pvo.getUseYn())
                .srtOdr(pvo.getSrtOdr())
                .codeDetail(pvo.getCodeDetail())
                .build());
    }

    /* 공통코드 조회 */
    public List<CommonCode> commonCodeList(CommonCodePVO pvo) {
        return commonCodeRepository.findAllByCommonTypeAndUseYnOrderBySrtOdr(pvo.getCommonType(), pvo.getUseYn());
    }
}
