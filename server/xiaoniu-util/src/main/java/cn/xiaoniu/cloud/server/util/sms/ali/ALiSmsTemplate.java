package cn.xiaoniu.cloud.server.util.sms.ali;

/**
 * @author 孔明
 * @date 2019/12/17 13:17
 * @description cn.xiaoniu.cloud.server.util.sms.ali.ALiSmsTemplate
 */
enum ALiSmsTemplate {

    /**
     * 注册短信
     */
    REGISERT("", SmsALiConstants.COMMON_TEMPLATE, ""),

    /**
     * 登录
     */
    LOGIN("", SmsALiConstants.COMMON_TEMPLATE, ""),

    /**
     * 重置密码
     */
    RESET_PWD("", SmsALiConstants.COMMON_TEMPLATE, "");

    private String code;

    private String param;

    private String description;

    ALiSmsTemplate(String code, String param, String description) {
        this.code = code;
        this.param = param;
        this.description = description;
    }

    public String code() {
        return this.code;
    }

    public String param(String... params) {
        return String.format(this.param, params);
    }

    public String getDescription() {
        return this.description;
    }
}
