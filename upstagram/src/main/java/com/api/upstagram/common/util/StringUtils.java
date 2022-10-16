package com.api.upstagram.common.util;

public class StringUtils {
    
    /* String null => Empty */
    public static String nullToBlank(String str) {
		
		return str == null ? "" : str;
	}
}
