package com.changhong.sei.notify.manager.sms;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.commons.util.EncodeUtils;
import com.changhong.sei.notify.config.SmsProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现功能: SMS短信消息的处理类
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 20:23
 */
public final class MatrixSmsHandler implements AbstractSmsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MatrixSmsHandler.class);

    public static final String OAUTH_SIGNATURE = "oauth_signature";
    public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String OAUTH_VERSION = "oauth_version";
    public static final String OAUTH_NONCE = "oauth_nonce";

    /**
     * 发送消息通知
     *
     * @param phoneNums 发送的手机号
     * @param content   发送的消息
     */
    @Override
    public ResultData<Void> send(SmsProperties properties, String[] phoneNums, String content) throws Exception {
//        LOG.info("短信服务地址: {}, smsAppKey: {}, smsSecretKey: {}", smsHost, smsAppKey, smsSecretKey);
//        LOG.debug("模拟发送短信：{}", message.getContent());

        Map<String, String> obj = new HashMap<>();
        //手机号
        obj.put("userNumber", join(phoneNums, ","));
        //短信内容,签名默认【四川长虹】
        obj.put("content", content);

//            String smsHost = ContextUtil.getProperty("sei.notify.sms.host", "https://ccp-sms-api.changhong.com/v1/sms/");
        HttpPost httppost = new HttpPost(properties.getHost());
        //认证token
        httppost.addHeader("Authorization", sign(properties));
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("User-Agent", "imgfornote");
        httppost.setEntity(new StringEntity(JsonUtils.toJson(obj), "UTF-8"));
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse response = httpclient.execute(httppost);
        //检验状态码，如果成功接收数据
        int code = response.getStatusLine().getStatusCode();
        //返回json格式： {"id": "27JpL~j4vsL0LX00E00005","version": "abc"}
        String rev = EntityUtils.toString(response.getEntity());
        LOG.info("Response: {}, Status: {}", rev, code);
        if (200 == code) {
            return ResultData.success();
        } else {
            return ResultData.fail(rev);
        }
    }

    private String sign(SmsProperties properties) throws UnsupportedEncodingException {
//        String ak = ContextUtil.getProperty("sei.notify.sms.appKey", "1659d01555254cd482d3e2a7b1856388");
//        String sk = ContextUtil.getProperty("sei.notify.sms.secretKey", "e2e7edc042cd42cd82dc88fc753a2cb9");
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = EncodeUtils.getRandomString(8);

        //拼接字符串
        String[] arr = new String[5];
        arr[0] = OAUTH_CONSUMER_KEY + "=" + URLEncoder.encode(properties.getAppKey(), "UTF-8");
        arr[1] = OAUTH_NONCE + "=" + URLEncoder.encode(nonce, "UTF-8");
        arr[2] = OAUTH_SIGNATURE_METHOD + "=" + URLEncoder.encode("HMAC-SHA1", "UTF-8");
        arr[3] = OAUTH_VERSION + "=" + URLEncoder.encode("1.0", "UTF-8");
        arr[4] = OAUTH_TIMESTAMP + "=" + URLEncoder.encode(timestamp.toString(), "UTF-8");

        Arrays.sort(arr);

        String baseString = join(arr, "&");
        LOG.debug("签名拼接字符串: {}", baseString);
        String sign = URLEncoder.encode(EncodeUtils.hmacSha1(properties.getSecretKey(), baseString), "UTF-8");
        LOG.debug("签名: {}", sign);
//            String sign = EncodeUtils.hmac_sha1(sk,)
        //添加http头信息
        StringBuilder stringBuffer = new StringBuilder("OAuth realm=\"")
                .append(properties.getHost())
                .append("\",")
                .append(OAUTH_CONSUMER_KEY)
                .append("=\"")
                .append(properties.getAppKey())
                .append("\",")
                .append(OAUTH_SIGNATURE)
                .append("=\"")
                .append(sign)
                .append("\",")
                .append(OAUTH_SIGNATURE_METHOD)
                .append("=\"HMAC-SHA1\",")
                .append(OAUTH_NONCE)
                .append("=\"")
                .append(nonce)
                .append("\",")
                .append(OAUTH_TIMESTAMP)
                .append("=\"")
                .append(timestamp)
                .append("\",")
                .append(OAUTH_VERSION)
                .append("=\"1.0\"");

        LogUtil.debug("短信签名: {}", stringBuffer);

        return stringBuffer.toString();
    }

    private static String join(String[] strs, String flag) {
        StringBuilder strBuff = new StringBuilder();
        for (int i = 0, len = strs.length; i < len; i++) {
            strBuff.append(strs[i]);
            if (i < len - 1) {
                strBuff.append(flag);
            }
        }
        return strBuff.toString();
    }
}
