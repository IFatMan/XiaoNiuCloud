package cn.xiaoniu.cloud.server.util.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 孔明
 * @date 2019/12/17 10:31
 * @description cn.xiaoniu.cloud.server.util.sms.SmsResponse
 * 当使用该类时您应当按照以下步骤操作: <br />
 * 1. 使用isSuccess()方法判断当前请求是否成功; true: 业务执行成功; false: 业务执行失败
 * 2. 当第一步返回false时, 字段outId 为空, 字段data 存储产生错误的原因
 * 3. 当第一步返回true时, 字段outId为第三方订单ID, 字段data 为第三方返回的完整的返回数据(JSON或其他文本)
 */
@Getter
@Setter
@ToString
public class SmsResponse<T> {

    /**
     * 当status 值为 'SUCCESS' 时表示发送成功
     */
    public static final String SMS_SEND_SUCCESS = "OK";

    /**
     * 发送状态
     */
    private String status;

    /**
     * 外部流水扩展字段
     */
    private String outId;

    /**
     * 其他数据
     */
    private String data;

    /**
     * 请求
     */
    private SmsRequest<T> request;

    /**
     * 是否成功
     *
     * @return true: 请求成功; false: 请求失败
     */
    public boolean isSuccess() {
        return SMS_SEND_SUCCESS.equals(this.status);
    }

}
