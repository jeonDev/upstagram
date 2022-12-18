package com.api.upstagram.domain.MemberInfo.Repository;

import com.api.upstagram.domain.MemberInfo.Entity.MemberInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInfoHistoryRepository extends JpaRepository<MemberInfoHistory, String> {

}
