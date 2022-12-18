package com.api.upstagram.domain.CommonCode.Repository;

import java.util.List;

import com.api.upstagram.domain.CommonCode.Entity.CommonCode;
import com.api.upstagram.domain.CommonCode.Entity.CommonCodeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {
    List<CommonCode> findAllByCommonTypeAndUseYnOrderBySrtOdr(String commonType, String useYn);
}
