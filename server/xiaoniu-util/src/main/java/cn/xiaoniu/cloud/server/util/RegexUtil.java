package cn.xiaoniu.cloud.server.util;

import cn.hutool.core.util.ReUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具
 */
public class RegexUtil {
    private RegexUtil() {
    }

    /**
     * 隐藏数据
     *
     * @param content
     * @return
     */
    public static String hideData(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        return ReUtil.replaceAll(content, "\\S", "*");
    }

    /**
     * 隐藏数据 转JSON后隐藏
     *
     * @param content
     * @return
     */
    public static String hideData(Object content) {
        if (content == null) {
            return null;
        }
        return hideData(JsonUtil.toJson(content));
    }

    /**
     * 字符串是否和正则匹配
     *
     * @param text    字符串
     * @param pattern 正则表达式
     * @return
     */
    public static boolean matcher(String text, String pattern) {
        return Pattern.compile(pattern).matcher(text).matches();
    }

    /**
     * 验证是否是手机号
     *
     * @param phone 需要验证手机号
     * @return true: 是手机号; false: 不是手机号
     */
    public static boolean isPhone(String phone) {
        return matcher(phone, RegexExpression.PHONE_PATTERN);
    }

    /**
     * 在字符串[text]中获取符合正则[pattern]的所有子字符串
     *
     * @param text    字符串
     * @param pattern 正则表达式
     * @return java.util.List<java.lang.String> 正则匹配到的所有字符串
     * @author 孔明
     * @date 2019-12-19 10:08:41
     */
    public static List<String> getMatcherText(String text, String pattern) {
        AssertUtil.isNotNull(text, "RegexUtil: 参数[text]为空 !");
        AssertUtil.isNotNull(pattern, "RegexUtil: 参数[pattern]为空 !");
        Matcher matcher = Pattern.compile(pattern).matcher(text);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    /**
     * 正则表达式
     */
    private static class RegexExpression {

        /**
         * 手机号正则表达式 截止2019年6月最新 <br>
         * 2019年1月16日已知: <br>
         * 中国电信号段: <br>
         * 133,149,153,173,174,177,180,181,189,199 <br>
         * 中国联通号段: <br>
         * 130,131,132,145,146,155,156,166,175,176,185,186<br>
         * 中国移动号段: <br>
         * 134(0-8),135,136,137,138,139,147,148,150,151,152,157,158,159,165,178,182,183,184,187,188,198 <br>
         * 上网卡专属号段（用于上网和收发短信，不能打电话）<br>
         * 如中国联通的是145<br>
         * 虚拟运营商<br>
         * 电信：1700,1701,1702<br>
         * 移动：1703,1705,1706<br>
         * 联通：1704,1707,1708,1709,171<br>
         * 卫星通信： 1349 <br>
         * 未知号段：141、142、143、144、154
         */
        private static final String PHONE_PATTERN = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
    }
}
