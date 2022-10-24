package com.api.upstagram.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.api.upstagram.common.vo.User;

public class CommonUtils {
    
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

    public static String uploadFile(MultipartFile file, String filePath, String[] exts) 
			throws IOException{
		
		UUID uuid 			= UUID.randomUUID();
		String fileName 	= file.getOriginalFilename();
		String realName		= uuid.toString() + "_" + fileName.replaceAll(" ", "");
		String contentType	= file.getContentType();
		String absolutePath = new File("").getAbsolutePath() + "//";
		int extSize 		= 0;
		
		for(String type : exts) {
			if(contentType.contains(type)) {
				extSize++;
			}
		}
		
		if(extSize < 1) {
			throw new IOException(contentType + " 확장자는 업로드 할 수 없습니다.");
		}
		
		File dest = new File(filePath);
		
		// 디렉토리가 없으면 생성
		if(!dest.exists()) {
			dest.mkdirs();
		}
		
		dest = new File(absolutePath + filePath + "/" + realName);
		
		file.transferTo(dest);
		
		return realName;
	}
}
