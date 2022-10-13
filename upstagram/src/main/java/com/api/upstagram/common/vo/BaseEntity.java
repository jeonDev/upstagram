package com.api.upstagram.common.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // Auditing(자동으로 값 매핑) 기능 추가
public class BaseEntity {

    @CreatedDate
    @Column(name="REG_DTTM", updatable = false, insertable = true)
    private LocalDateTime regDttm;
    
    @LastModifiedDate
    @Column(name="LAST_DTTM")
    private LocalDateTime lastDttm;

}