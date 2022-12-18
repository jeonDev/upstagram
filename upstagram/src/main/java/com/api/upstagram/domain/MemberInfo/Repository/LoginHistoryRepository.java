package com.api.upstagram.domain.MemberInfo.Repository;

import com.api.upstagram.domain.MemberInfo.Entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    
}
