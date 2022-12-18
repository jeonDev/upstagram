package com.api.upstagram.controller;

import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.service.CommonService;
import com.api.upstagram.vo.CommonCode.CommonCodePVO;
import com.api.upstagram.vo.CommonCode.CommonCodeRVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/admin/common/code/regist")
    public ResponseVO<CommonCodeRVO> commonCodeRegister(@RequestBody CommonCodePVO pvo) {
        log.info(this.getClass().getName() + " ==> 공통코드 등록");
        ResponseVO<CommonCodeRVO> r = new ResponseVO<CommonCodeRVO>();

        CommonCodeRVO rvo = commonService.commonCodeRegister(pvo).commonCodeToRVO();

        r.setData(rvo);

        return r;
    }

    @PostMapping("/admin/common/code/update")
    public ResponseVO<CommonCodeRVO> commonCodeUpdate(@RequestBody CommonCodePVO pvo) {
        log.info(this.getClass().getName() + " ==> 공통코드 수정");
        ResponseVO<CommonCodeRVO> r = new ResponseVO<CommonCodeRVO>();

        CommonCodeRVO rvo = commonService.commonCodeUpdate(pvo).commonCodeToRVO();

        r.setData(rvo);

        return r;
    }

    /*
     * 공통코드 조회
     */
    @GetMapping("/common/code/list")
    public ResponseVO<List<CommonCodeRVO>> commonCodeList(@RequestParam String commonType, @RequestParam String useYn) {
        log.info(this.getClass().getName() + " ==> 공통코드 조회!");
        ResponseVO<List<CommonCodeRVO>> r = new ResponseVO<List<CommonCodeRVO>>();

        CommonCodePVO pvo = new CommonCodePVO();
        pvo.setCommonType(commonType);
        pvo.setUseYn(useYn);
        
        List<CommonCodeRVO> rvo = commonService.commonCodeList(pvo).stream()
                                .map(m -> m.commonCodeToRVO())
                                .collect(Collectors.toList());
        
        r.setData(rvo);

        return r;
    }
}
