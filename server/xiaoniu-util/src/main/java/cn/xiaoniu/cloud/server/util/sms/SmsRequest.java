package cn.xiaoniu.cloud.server.util.sms;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 孔明
 * @date 2019/12/17 13:46
 * @description cn.xiaoniu.cloud.server.util.sms.SmsRequest
 */
@Getter
@Setter
public class SmsRequest<T> {

    /**
     * 手机号
     */
    private T phoneNumbers;

    /**
     * 参数
     */
    private String[] params;

    protected SmsRequest(T phoneNumbers, String... params) {
        this.phoneNumbers = phoneNumbers;
        this.params = params;
    }
}
