package com.api.upstagram.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)  // Auditing(자동으로 값 매핑) 기능 추가
public class BaseIdEntity extends BaseEntity{
    
    @CreatedBy
    @Column(name="REG_ID", updatable = false, insertable = true)
    private String regId;

    @LastModifiedBy
    @Column(name = "LAST_ID")
    private String lastId;
}
