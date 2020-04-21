package cn.xiaoniu.cloud.server.util.sms.ali;

import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.sms.SmsResponse;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * 阿里云短信Util
 *
 * @author 孔明
 * @date 2019/12/16 19:26
 * @description cn.xiaoniu.cloud.server.util.sms.ali.SmsALiUtil
 */
@Slf4j
public class SmsALiUtil {

    private SmsALiUtil() {
    }

    /**
     * 请求客户端
     */
    private static IAcsClient client;

    static {
        DefaultProfile profile = DefaultProfile.getProfile(SmsALiConstants.Config.REGION_ID,
                SmsALiConstants.Config.ACCESS_KEY_ID, SmsALiConstants.Config.ACCESS_KEY_SECRET);
        client = new DefaultAcsClient(profile);
    }

    /**
     * 注册短信
     *
     * @param phoneNumber 手机号
     * @param randomCode  随机验证码
     * @return SmsResponse <br />
     * @see cn.xiaoniu.cloud.server.util.sms.SmsResponse
     */
    public static SmsResponse<Set<String>> registerMsg(String phoneNumber, String randomCode) {
        SmsAliRequest request = SmsAliRequest.builder(phoneNumber, randomCode);
        request.setAction(ALiSmsAction.SEND_SMS);
        request.setTemplate(ALiSmsTemplate.REGISERT);

        return execute(request);
    }

    /**
     * 登录短信
     *
     * @param phoneNumber 手机号
     * @param randomCode  随机验证码
     * @return SmsResponse <br />
     * @see cn.xiaoniu.cloud.server.util.sms.SmsResponse
     */
    public static SmsResponse<Set<String>> loginMsg(String phoneNumber, String randomCode) {
        SmsAliRequest request = SmsAliRequest.builder(phoneNumber, randomCode);
        request.setAction(ALiSmsAction.SEND_SMS);
        request.setTemplate(ALiSmsTemplate.LOGIN);

        return execute(request);
    }

    /**
     * 重置密码短信
     *
     * @param phoneNumber 手机号
     * @param randomCode  随机验证码
     * @return SmsResponse <br />
     * @see cn.xiaoniu.cloud.server.util.sms.SmsResponse
     */
    public static SmsResponse<Set<String>> resetPwdMsg(String phoneNumber, String randomCode) {
        SmsAliRequest request = SmsAliRequest.builder(phoneNumber, randomCode);
        request.setAction(ALiSmsAction.SEND_SMS);
        request.setTemplate(ALiSmsTemplate.RESET_PWD);

        return execute(request);
    }

    /**
     * 为多个手机号批量发送同一条短信 , 最多1000个手机号<br/>
     * <p>
     * Demo:<br/>
     * Set<String> phoneNumbers = new HashSet<>(2);<br/>
     * phoneNumbers.add("phone1");<br/>
     * phoneNumbers.add("phone2");<br/>
     * SmsAliRequest request1 = SmsAliRequest.builder(phoneNumbers, "1111");<br/>
     * request1.setAction(action);<br/>
     * request1.setTemplate(template);<br/>
     * </p>
     *
     * @param request SmsAliRequest
     * @return SmsResponse <br />
     * @see SmsAliRequest
     * @see cn.xiaoniu.cloud.server.util.sms.SmsResponse
     */
    public static SmsResponse batchMsg(SmsAliRequest request) {
        return execute(request);
    }

    /**
     * 执行发送操作
     *
     * @param request CommonRequest
     * @author 孔明
     * @date 2019-12-17 13:12:01
     */
    private static SmsResponse<Set<String>> execute(SmsAliRequest request) {
        String logMsg = String.format("为手机号%s发送%s", request.getPhoneNumbers().toString(), request.getTemplate().getDescription());

        log.debug("-- {}开始 !", logMsg);
        // 本系统Response
        SmsResponse<Set<String>> smsResponse = new SmsResponse<>();
        smsResponse.setRequest(request);

        // 检验手机号格式是否有错误
        String errorPhone = request.checkPhoneNumber();
        if (StringUtils.isNotBlank(errorPhone)) {
            smsResponse.setData(String.format("-- %s错误: 手机号[%s]格式错误 !", logMsg, errorPhone));
            return smsResponse;
        }

        try {
            // 接口请求耗时计算开始
            long start = System.currentTimeMillis();

            // 创建ALi请求对象
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setMethod(MethodType.POST);
            commonRequest.setDomain(SmsALiConstants.Config.DOMAIN);
            commonRequest.setVersion(SmsALiConstants.Config.VERSION);
            commonRequest.setAction(request.getAction().value());
            commonRequest.putQueryParameter("PhoneNumbers", StringUtils.join(request.getPhoneNumbers().toArray()));
            commonRequest.putQueryParameter("SignName", SmsALiConstants.Config.SIGN_NAME);
            commonRequest.putQueryParameter("TemplateCode", request.getTemplate().code());
            commonRequest.putQueryParameter("TemplateParam", request.getTemplate().param(request.getParams()));

            // 发送请求打印日志
            CommonResponse response = client.getCommonResponse(commonRequest);
            log.debug("-- {}返回:{};耗时: {}/毫秒", logMsg, JsonUtil.toJson(response), System.currentTimeMillis() - start);

            // 解析ALi Response ,封装成本系统Response
            if (Objects.isNull(response) || StringUtils.isBlank(response.getData())) {
                log.debug("-- {}错误: 返回为空或者未返回数据 !", logMsg);

                smsResponse.setData("请求错误: 返回为空或者未返回数据 !");
                return smsResponse;
            }

            String responseData = response.getData();
            String code = JsonUtil.getValueByKey(responseData, "Code");
            if (!SmsResponse.SMS_SEND_SUCCESS.equals(code)) {
                smsResponse.setData(JsonUtil.getValueByKey(responseData, "Message"));
                return smsResponse;
            }

            smsResponse.setStatus(SmsResponse.SMS_SEND_SUCCESS);
            smsResponse.setOutId(JsonUtil.getValueByKey(responseData, "BizId"));
            smsResponse.setData(responseData);

            log.debug("-- {}成功, 消息返回:{} !", logMsg, smsResponse);
            return smsResponse;
        } catch (Exception ex) {
            log.error("-- {}异常:{}", logMsg, ex);

            smsResponse.setData("SmsAliUtil: 程序异常! ");
            return smsResponse;
        }
    }

}
