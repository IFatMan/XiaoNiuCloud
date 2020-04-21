package cn.xiaoniu.cloud.server.util.sms.ali;

/**
 * 短信类别
 *
 * @author 孔明
 * @date 2019/12/17 13:16
 * @description cn.xiaoniu.cloud.server.util.sms.ali.ALiSmsAction
 */
enum ALiSmsAction {

    /**
     * 发送短信
     */
    SEND_SMS("SendSms");

    private String value;

    ALiSmsAction(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
