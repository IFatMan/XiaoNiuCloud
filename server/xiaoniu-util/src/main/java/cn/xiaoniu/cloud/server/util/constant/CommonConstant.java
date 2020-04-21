package cn.xiaoniu.cloud.server.util.constant;

import java.nio.charset.Charset;

/**
 * 公共常量定义
 *
 * @author 孔明
 * @date 2019/11/22 15:39
 * @description cn.xiaoniu.cloud.server.util.constant.CommonConstant
 */
public final class CommonConstant {

    private CommonConstant() {
    }

    /**
     * YES
     */
    public static final Integer YES = 1;

    /**
     * NO
     */
    public static final Integer NO = 0;

    /**
     * requestId 在日志中的打印格式
     */
    public static final String REQUEST_ID_LOG = "%s - %s ";

    /**
     * 字符串类型 UTF-8
     */
    public static final String UTF_8_S = "UTF-8";

    /**
     * Charset类型 UTF-8编码
     */
    public static final Charset UTF_8_C = Charset.forName(UTF_8_S);

    /**
     * ':' 分隔符
     */
    public static final String CHAR_COLON = ":";

    /**
     * '-' 分隔符
     */
    public static final String CHAR_LINE_THROUGH = "-";

    /**
     * '&' 分隔符
     */
    public static final String CHAR_AND = "&";

    /**
     * '=' 分隔符
     */
    public static final String CHAR_EQUAL = "=";

    /**
     * '.' 分隔符
     */
    public static final String CHAR_DOT = ".";

    /**
     * '/' 分隔符
     */
    public static final String CHAR_SLASH = "/";

    /**
     * '?' 分隔符
     */
    public static final String CHAR_QUESTION_MARK = "?";

    /**
     * "" 空字符串
     */
    public static final String CHAR_EMPTY = "";

    /**
     * ',' 英文逗号
     */
    public static final String CHAR_COMMA = ",";

    /**
     * '(' 左括号
     */
    public static final String CHAR_LEFT_BRACKETS = "(";

    /**
     * ')' 右括号
     */
    public static final String CHAR_RIGHT_BRACKETS = ")";

}
