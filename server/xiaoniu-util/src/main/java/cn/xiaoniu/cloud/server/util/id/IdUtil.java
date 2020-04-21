package cn.xiaoniu.cloud.server.util.id;

import cn.xiaoniu.cloud.server.util.constant.CommonConstant;

import java.util.UUID;

/**
 * Snowflake ID 唯一生成器
 */
public class IdUtil {

    /**
     * workerId 工作ID (0~31)
     */
    private static final int WORK_ID = 1;

    /**
     * 数据中心ID (0~31)
     */
    private static final int DATA_CENTER_ID = 1;

    /**
     * Snowflake 算法对象
     */
    private static Snowflake snowflakeNumber;

    /**
     * 私有化构造器
     */
    private IdUtil() {
    }

    /**
     * 使用 Snowflake 算法生成ID
     */
    private static Snowflake getSnowflake() {
        if (IdUtil.snowflakeNumber == null) {
            IdUtil.snowflakeNumber = new Snowflake(WORK_ID, DATA_CENTER_ID);
        }
        return IdUtil.snowflakeNumber;
    }

    /**
     * 获取数字ID
     *
     * @return 18位数字ID
     */
    public static long getNumberId() {
        return getSnowflake().nextNumberId();
    }

    /**
     * 获取带有日期ID;
     * 日期默认格式:yyyyMMddHHmmssSSS
     *
     * @return 23位以日期开头的ID(20191122160842603135169)
     */
    public static String getDateId() {
        return getSnowflake().nextDateId();
    }

    /**
     * UUID 生成,替换所有的"-"为"",并转大写
     *
     * @return java.lang.String
     * @author 孔明
     * @date 2019-11-22 16:10:11
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace(CommonConstant.CHAR_LINE_THROUGH, CommonConstant.CHAR_EMPTY).toUpperCase();
    }
}
