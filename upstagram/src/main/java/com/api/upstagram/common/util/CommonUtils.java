package com.api.upstagram.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    
    public static String dateToYmdString(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }
}
