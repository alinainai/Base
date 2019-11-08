package com.base.lib.util.sharedpre;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {


    private EncryptUtil(){
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     *
     * @param key
     * @return
     */
    public static String encrptKey(String key){
        return md5(key);
    }

    public static String encrptValue(String value){
        return base64Encode(value);
    }

    public static String decddeValue(String value){
        return base64Decode(value);
    }

    @SuppressWarnings("CharsetObjectCanBeUsed")
    public static String md5(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String base64Encode(String content) {
        return Base64.encodeToString(content.getBytes(), Base64.NO_WRAP);
    }

    public static String base64Decode(String content) {
        return new String(Base64.decode(content.getBytes(), Base64.NO_WRAP));
    }


}
