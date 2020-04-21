package cn.xiaoniu.cloud.server.util.sms.ali;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.util.AssertUtil;
import cn.xiaoniu.cloud.server.util.RegexUtil;
import cn.xiaoniu.cloud.server.util.sms.SmsRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 孔明
 * @date 2019/12/17 13:45
 * @description cn.xiaoniu.cloud.server.util.sms.ali.SmsAliRequest
 */
@Getter
@Setter
public class SmsAliRequest extends SmsRequest<Set<String>> {

    /**
     * 动作
     */
    private ALiSmsAction action;

    /**
     * 模板
     */
    private ALiSmsTemplate template;

    private SmsAliRequest(Set<String> phoneNumbers, String... params) {
        super(phoneNumbers, params);
    }

    /**
     * 构建Request
     *
     * @param phoneNumber 手机号
     * @param params      参数
     * @return
     */
    public static SmsAliRequest builder(String phoneNumber, String... params) {
        Set<String> phoneNumbers = new HashSet<>(1);
        phoneNumbers.add(phoneNumber);
        return new SmsAliRequest(phoneNumbers, params);
    }

    /**
     * 构建Request
     *
     * @param phoneNumbers 手机号
     * @param params       参数
     * @return
     */
    public static SmsAliRequest builder(Set<String> phoneNumbers, String... params) {
        if (CollUtil.isEmpty(phoneNumbers)) {
            AssertUtil.isTrue(false, "SmsRequest: 构造函数参数 phoneNumbers 为空 !");
        }
        if (phoneNumbers.size() > 1000) {
            AssertUtil.isTrue(false, "SmsRequest: 手机号最多 1000 个!");
        }
        return new SmsAliRequest(phoneNumbers, params);
    }

    /**
     * 验证手机号是否符合要求,返回第一个不符合要求的手机号<br/>
     * 返回null 表示全部符合要求
     */
    public String checkPhoneNumber() {
        for (String phoneNumber : super.getPhoneNumbers()) {
            if (!RegexUtil.isPhone(phoneNumber)) {
                return phoneNumber;
            }
        }
        return null;
    }

}
