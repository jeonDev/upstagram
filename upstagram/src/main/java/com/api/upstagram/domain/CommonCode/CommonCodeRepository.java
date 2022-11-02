package com.api.upstagram.domain.CommonCode;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {
    List<CommonCode> findAllByCommonTypeAndUseYnOrderBySrtOdr(String commonType, String useYn);
}
