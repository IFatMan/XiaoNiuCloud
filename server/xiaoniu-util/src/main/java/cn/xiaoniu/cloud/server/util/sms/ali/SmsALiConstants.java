package cn.xiaoniu.cloud.server.util.sms.ali;

/**
 * @author 孔明
 * @date 2019/12/17 13:19
 * @description cn.xiaoniu.cloud.server.util.sms.ali.SmsALiConstants
 */
class SmsALiConstants {

    private SmsALiConstants() {
    }

    /**
     * 公共短信模板
     */
    static final String COMMON_TEMPLATE = "{\"code\":\"%s\"}";

    /**
     * 无效的手机号码
     */
    static final String CHECK_IS_PHONE_ERROR_MSG = "无效的手机号码 !";

    /**
     * 配置
     */
    static class Config {

        /**
         * 域名
         */
        static final String DOMAIN = "dysmsapi.aliyuncs.com";

        /**
         * 版本号
         */
        static final String VERSION = "2017-05-25";

        /**
         * ALi服务器地址
         */
        static final String REGION_ID = "cn-hangzhou";

        /**
         * 签名
         */
        static final String SIGN_NAME = "小牛导航";

        /**
         * AccessKeyId 在阿里云平台创建
         */
        static final String ACCESS_KEY_ID = "";

        /**
         * AccessKeySecret 在阿里云平台创建 AK 的时候显示
         */
        static final String ACCESS_KEY_SECRET = "";

    }

}
