package com.api.upstagram.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.api.upstagram.common.security.Jwt.JwtTokenProvider;
import com.api.upstagram.common.vo.User;

public class CommonUtils {
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    public static String dateToYmdString(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }

    /*
     * SecurityContext에 저장된 토큰정보에서 User객체 가져오기
     */
    public static User getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = new User();
		
		if(authentication.getPrincipal() instanceof User) {
			user = (User) authentication.getPrincipal();
		}

        return user;
    }

    /*
     * SecurityContext에 저장된 토큰정보에서 ID 값 가져오기
     */
    public static String getUserId() {
        User user = getUserInfo();
        return user.getId() != null ? user.getId() : "";
    }
}
