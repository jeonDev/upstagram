package com.api.upstagram.controller;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.domain.CommonCode.CommonCode;
import com.api.upstagram.service.CommonService;
import com.api.upstagram.vo.CommonCode.CommonCodePVO;
import com.api.upstagram.vo.CommonCode.CommonCodeRVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/admin/common/code/regist")
    public ResponseVO<CommonCodeRVO> commonCodeRegister(@RequestBody CommonCodePVO pvo) {
        ResponseVO<CommonCodeRVO> r = new ResponseVO<CommonCodeRVO>();

        CommonCode commonCode = commonService.commonCodeRegister(pvo);
        CommonCodeRVO rvo = CommonCodeRVO.builder()
                .commonCode(commonCode.getCommonCode())
                .commonCodeName(commonCode.getCommonCodeName())
                .commonType(commonCode.getCommonType())
                .useYn(commonCode.getUseYn())
                .srtOdr(commonCode.getSrtOdr())
                .codeDetail(commonCode.getCodeDetail())
                .build();

        r.setData(rvo);

        return r;
    }

    @PostMapping("/admin/common/code/update")
    public ResponseVO<CommonCodeRVO> commonCodeUpdate(@RequestBody CommonCodePVO pvo) {
        ResponseVO<CommonCodeRVO> r = new ResponseVO<CommonCodeRVO>();

        CommonCode commonCode = commonService.commonCodeUpdate(pvo);
        CommonCodeRVO rvo = CommonCodeRVO.builder()
                .commonCode(commonCode.getCommonCode())
                .commonCodeName(commonCode.getCommonCodeName())
                .commonType(commonCode.getCommonType())
                .useYn(commonCode.getUseYn())
                .srtOdr(commonCode.getSrtOdr())
                .codeDetail(commonCode.getCodeDetail())
                .build();

        r.setData(rvo);

        return r;
    }
}
