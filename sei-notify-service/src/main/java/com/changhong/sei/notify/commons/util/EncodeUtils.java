package com.changhong.sei.notify.commons.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by wei on 14-1-20.
 */
public class EncodeUtils {
    public static String hmacSha1(String key, String data) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ignore) {
        }

        String oauth = Base64.encodeBase64String(byteHMAC);
        return oauth;
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
//      String data = "oauth_consumer_key=abcdefg&oauth_nonce=03ggdh&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1393468028&oauth_version=1.0";
        String data = "oauth_consumer_key=6b05dd4fc9054dc6x17224d27d052312&oauth_nonce=qbMYDKes&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1419251247000&oauth_version=1.0";
        System.out.println(hmacSha1("9521b0b61af374456979383a1627d147", data));
        System.out.println(URLEncoder.encode(hmacSha1("9521b0b61af374456979383a1627d147", data), "utf-8"));
    }
}
