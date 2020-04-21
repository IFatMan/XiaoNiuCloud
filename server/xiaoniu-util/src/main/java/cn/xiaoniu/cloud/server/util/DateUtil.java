package cn.xiaoniu.cloud.server.util;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author 孔明
 * @date 2019/12/17 17:29
 * @description cn.xiaoniu.cloud.server.util.DateUtil
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * 获取今日日期<br/>
     * 格式: yyyy-MM-dd
     *
     * @return java.lang.String
     * @author 孔明
     * @date 2019-12-17 17:31:43
     */
    public static String getCurrDate() {
        return LocalDate.now().toString();
    }

    /**
     * 获取当前日期开始时间<br/>
     * 格式: yyyy-MM-dd 00:00:00:000
     *
     * @return java.util.Date
     * @author 孔明
     * @date 2019-12-17 18:11:21
     */
    public static Date getCurrDateStart() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.of(0, 0, 0, 0);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = LocalDateTime.of(localDate, localTime).atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前日期结束时间<br/>
     * 格式: yyyy-MM-dd 23:59:59:999
     *
     * @return java.util.Date
     * @author 孔明
     * @date 2019-12-17 18:11:21
     */
    public static Date getCurrDateEnd() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.of(23, 59, 59, 999);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = LocalDateTime.of(localDate, localTime).atZone(zoneId).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前月的最后一天
     *
     * @return int
     * @author 孔明
     * @date 2020-01-07 11:36:05
     */
    public static int getMaxDayOfCurrMouth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.getMaximum(Calendar.DAY_OF_MONTH);
    }
}
