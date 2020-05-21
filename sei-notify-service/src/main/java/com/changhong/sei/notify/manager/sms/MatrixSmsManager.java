package com.changhong.sei.notify.manager.sms;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.commons.util.EncodeUtils;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.manager.NotifyManager;
import com.changhong.sei.notify.dto.SendMessage;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <strong>实现功能:</strong>
 * <p>SMS短信消息的处理类</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-18 20:23
 */
@Component("SMS")
public class MatrixSmsManager implements NotifyManager {
    private static final Logger LOG = LoggerFactory.getLogger(MatrixSmsManager.class);

    public static final String TIMESTAMP = "timestamp";
    public static final String OAUTH_SIGNATURE = "oauth_signature";
    public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String OAUTH_VERSION = "oauth_version";
    public static final String OAUTH_NONCE = "oauth_nonce";

    /**
     * 发送消息通知
     *
     * @param message 发送的消息
     */
    @Override
    public ResultData<String> send(SendMessage message) {
        LOG.debug("模拟发送短信：{}", message.getContent());
        try {
            HttpClient httpclient = new DefaultHttpClient();
//            String uri = "http://api.chiq-cloud.com/v1/sms/";
            String uri = "https://ccp-sms-api.changhong.com/v1/sms/";
//            String uri = "https://ccp-sms-api.changhong.com/";

            HttpPost httppost = new HttpPost(uri);

            httppost.addHeader("Authorization", sign(uri)); //认证token
            httppost.addHeader("Content-Type", "application/json");
            httppost.addHeader("User-Agent", "imgfornote");
            Map<String, String> obj = new HashMap<>();

            // 排重
            Set<String> numSet = new HashSet<>();
            for (UserNotifyInfo info : message.getReceivers()) {
                numSet.add(info.getMobile());
            }
            String[] strs = numSet.toArray(new String[numSet.size()]);

            //手机号
            obj.put("userNumber", join(strs, ","));
            //短信内容,签名默认【四川长虹】
            obj.put("content", message.getContent());
            httppost.setEntity(new StringEntity(JsonUtils.toJson(obj), "UTF-8"));
            HttpResponse response;
            response = httpclient.execute(httppost);
            //检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            //返回json格式： {"id": "27JpL~j4vsL0LX00E00005","version": "abc"}
            String rev = EntityUtils.toString(response.getEntity());
            LOG.info("Response: {}, Status: {}", rev, code);
            if (code == 200) {
                obj = JsonUtils.fromJson(rev, Map.class);
                String id = MapUtils.getString(obj, "id");
                String version = MapUtils.getString(obj, "version");
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return ResultData.success("OK");
    }

    public static String sign(String uri) throws UnsupportedEncodingException {
        String ak = ContextUtil.getProperty("sei.sms.appKey", "1659d01555254cd482d3e2a7b1856388");
        String sk = ContextUtil.getProperty("sei.sms.secretKey", "e2e7edc042cd42cd82dc88fc753a2cb9");
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = EncodeUtils.getRandomString(8);

        //拼接字符串
        String[] arr = new String[5];
        arr[0] = OAUTH_CONSUMER_KEY + "=" + URLEncoder.encode(ak, "UTF-8");
        arr[1] = OAUTH_NONCE + "=" + URLEncoder.encode(nonce, "UTF-8");
        arr[2] = OAUTH_SIGNATURE_METHOD + "=" + URLEncoder.encode("HMAC-SHA1", "UTF-8");
        arr[3] = OAUTH_VERSION + "=" + URLEncoder.encode("1.0", "UTF-8");
        arr[4] = OAUTH_TIMESTAMP + "=" + URLEncoder.encode(timestamp.toString(), "UTF-8");

        Arrays.sort(arr);

//        Map<String, String> oauth = new TreeMap<>();
//        oauth.put(OAUTH_CONSUMER_KEY, ak);
//        oauth.put(OAUTH_NONCE, nonce);
//        oauth.put(OAUTH_SIGNATURE_METHOD, "HMAC-SHA1");
//        oauth.put(OAUTH_VERSION, "1.0");
//        oauth.put(OAUTH_TIMESTAMP, timestamp);

        String baseString = join(arr, "&");
        LOG.debug("签名拼接字符串: {}", baseString);
        String sign = URLEncoder.encode(EncodeUtils.hmacSha1(sk, baseString), "UTF-8");
        LOG.debug("签名: {}", sign);
//            String sign = EncodeUtils.hmac_sha1(sk,)
        //添加http头信息
        StringBuilder stringBuffer = new StringBuilder("OAuth realm=\"")
                .append(uri)
                .append("\",")
                .append(OAUTH_CONSUMER_KEY)
                .append("=\"")
                .append(ak)
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
                .append("=\"1.0");

        return stringBuffer.toString();
    }

    private static String join(String[] strs, String flag) {
        StringBuilder str_buff = new StringBuilder();
        for (int i = 0, len = strs.length; i < len; i++) {
            str_buff.append(strs[i]);
            if (i < len - 1) {
                str_buff.append(flag);
            }
        }
        return str_buff.toString();
    }
}
