package com.api.upstagram.common.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.api.upstagram.common.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class IdAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String id = CommonUtils.getUserId();
        return Optional.ofNullable(id);
    }
    
}
