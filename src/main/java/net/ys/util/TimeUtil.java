package net.ys.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: NMY
 * @Date: 2020/7/21
 * @Time: 16:09
 */
public class TimeUtil {

    /**
     * 获取日期
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    /**
     * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
     *
     * @param date
     * @return
     */
    public static String getISO8601Timestamp(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        String nowAsISO = df.format(date);
        return nowAsISO;
    }
}
