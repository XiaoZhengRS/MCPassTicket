package com.xiaozhengkeji.mcpassticket.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
    /**
     * 取现行时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getSysDate() {
        long now = System.currentTimeMillis();
        Date d = new Date();
        d.setTime(now);
        SimpleDateFormat forMat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return forMat.format(d);
    }
}
