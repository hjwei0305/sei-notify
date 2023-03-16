package com.changhong.sei.notify.manager.sms;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.config.SmsProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: chenzhiquan
 * @Date: 2023/3/15.
 */
public class XbSmsHandler implements AbstractSmsHandler {
    @Override
    public ResultData<Void> send(SmsProperties properties, String[] phoneNums, String content) throws Exception {
        for (String phoneNum : phoneNums) {
          if(sendSms(phoneNum,content))  {
              return ResultData.success();
          }else{
              return ResultData.fail("失败");
          }
        }
        return ResultData.fail("失败");
    }

    /**
     * 调用短信平台发送短信
     * @param mobile 手机号码
     * @param msg 短信内容
     * @return
     */
    public boolean sendSms(String mobile, String msg) {
        String xbtitle = "【广东新宝电器】";
        String appid = "0K17l8KNYUCreuFprhoidt3odEGlHu4Y";//该值由电信提供
        String appkey = "vFh2qPZg0qUO7xP1mhL09ZvbskZLWNOr";//由电信提供(可登录系统重置)
        msg = xbtitle + msg;//取短信内容
        String urlsms = null;
        try {
            urlsms = new String(msg.getBytes("utf-8"));
            //对短信内容进行UTF8编码
            String sign = appid + mobile + msg + appkey;//MSG为UTF8编码前内容
            String sendSign = encrypt3ToMD5(sign);
            sendSign = sendSign.toLowerCase();//转为小写
            //请求地址
            String url = "http://sms.189ek.com/yktsms/send?appid=" + appid + "&mobile=" + mobile + "&msg=" + urlsms + "&sign=" + sendSign;
            Map<String, String> obj = new HashMap<>();
            HttpPost httppost = new HttpPost(url);
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            String rev = null;
            try {
                rev = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (200 == code) {
                return true;
            } else {
                return false;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 加密
     *
     * @param str 需要加密的字符
     * @return
     */
    public static String encrypt3ToMD5(String str) {
        String md5 = "";
        try {
            md5 = DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
