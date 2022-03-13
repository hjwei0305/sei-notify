package com.changhong.sei.notify.manager.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.config.SmsProperties;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 实现功能：阿里云短消息发送处理器
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-24 23:37
 */
public class AliSmsHandler implements AbstractSmsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AliSmsHandler.class);

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 发送消息通知
     *
     * @param properties 配置对象
     * @param phoneNums  发送的手机号
     * @param content    发送的消息
     */
    @Override
    public ResultData<Void> send(SmsProperties properties, String[] phoneNums, String content) throws Exception {
        TemplateData templateData = JsonUtils.fromJson(content, TemplateData.class);
        if (Objects.nonNull(templateData)) {
            Client client = createClient(properties.getAppKey(), properties.getSecretKey());
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    // 短信签名名称
                    .setSignName(templateData.getSignName())
                    // 接收短信的手机号码.支持对多个手机号码发送短信，手机号码之间以半角逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
                    .setPhoneNumbers(StringUtils.join(phoneNums, ","))
                    // 上行短信扩展码
                    .setSmsUpExtendCode(templateData.getSmsUpExtendCode())
                    // 外部流水扩展字段
                    .setOutId(templateData.getOutId())
                    // 短信模板CODE
                    .setTemplateCode(templateData.getTemplateCode())
                    // 短信模板变量对应的实际值
                    .setTemplateParam(JsonUtils.toJson(templateData.getTemplateParam()));
            // 复制代码运行请自行打印 API 的返回值 {"code":"123456"}
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            if (Objects.nonNull(sendSmsResponse)) {
                SendSmsResponseBody body = sendSmsResponse.getBody();
                if (Objects.nonNull(body)) {
                    if ("OK".equals(body.getCode())) {
                        return ResultData.success();
                    } else {
                        return ResultData.fail(body.getMessage());
                    }
                }
            }
            // 短信发送失败.
            return ResultData.fail(ContextUtil.getMessage("00027"));
        } else {
            // 在平台内的短信消息模版配置错误
            return ResultData.fail(ContextUtil.getMessage("00026"));
        }
    }

    private static class TemplateData {
        /**
         * 短信签名名称
         */
        private String signName;
        /**
         * 外部流水扩展字段
         */
        private String outId;
        /**
         * 上行短信扩展码
         */
        private String smsUpExtendCode;
        /**
         * 短信模板CODE
         */
        private String templateCode;
        /**
         * 短信模板变量对应的实际值,JSON格式。支持传入多个参数，示例：{"name":"张三","number":"15038****76"}。
         */
        private Map<String, Object> templateParam;

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }

        public String getOutId() {
            return outId;
        }

        public void setOutId(String outId) {
            this.outId = outId;
        }

        public String getSmsUpExtendCode() {
            return smsUpExtendCode;
        }

        public void setSmsUpExtendCode(String smsUpExtendCode) {
            this.smsUpExtendCode = smsUpExtendCode;
        }

        public String getTemplateCode() {
            return templateCode;
        }

        public void setTemplateCode(String templateCode) {
            this.templateCode = templateCode;
        }

        public Map<String, Object> getTemplateParam() {
            return templateParam;
        }

        public void setTemplateParam(Map<String, Object> templateParam) {
            this.templateParam = templateParam;
        }
    }

}
