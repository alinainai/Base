package trunk.doi.base.util;

import android.net.Uri;
import android.util.Base64;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *  数据访问用作签名的类
 *  将参数和时间戳做加密最后导出签名
 *  与服务器端做验证防止第三方非法操作
 */

public class Encryption {

    /**
     * 返回URL和参数排序的结果
     * @param url URL地址
     */

    /**
     * 64位私有秘钥
     */
    public static String secretKey = "4704540132603844096724182068402814985072698556303674268151921210";

    // AES加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            return null;
        }
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return Base64.encodeToString(encrypted, Base64.NO_WRAP);


    }

    // AES解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //AES_CBC_
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.decode(sSrc, Base64.NO_WRAP);//
        try {
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * 用java的base64进行加密
     * 将 字符串 进行 BASE64 编码
     *
     * @param s
     * @return
     */
    public static String getBASE64(String s) {
        if (s == null) return null;
        return Base64.encodeToString(s.getBytes(), Base64.NO_WRAP);
    }

    /**
     * 签名生成算法
     *
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(HashMap<String, String> params, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(secret);

        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    public static String sequenceUrl(String url) {
        int i = 0;
        Uri uri = Uri.parse(url);
        Set<String> query = uri.getQueryParameterNames();
        StringBuilder sb = new StringBuilder(url);
        if (!query.isEmpty()) {
            TreeSet<String> treeQuery = new TreeSet<>(query);
            sb.setLength(0);
            String s = url.substring(0, url.indexOf("?"));
            sb.append(s);
            for (String key : treeQuery) {
                String value = uri.getQueryParameter(key);
                if (i == 0) {
                    i++;
                    sb.append("?").append(key).append("=").append(value);
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
        }
        return sb.append("&").append("secretKey").append("=").append(secretKey).toString();
    }
}
