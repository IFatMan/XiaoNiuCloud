package cn.xiaoniu.cloud.server.util.character;

/**
 * 字段工具
 *
 * @author 孔明
 * @date 2019/11/18 10:43
 * @description cn.xiaoniu.cloud.server.util.character.StringUtil
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 字段是否为空
     *
     * @param cs 需要判断字段
     * @return boolean
     * @author 孔明
     * @date 2019-11-18 10:45:56
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 字段是否为空
     *
     * @param cs 需要判断字段
     * @return boolean true:空 false:不为空
     * @author 孔明
     * @date 2019-11-18 10:47:14
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
