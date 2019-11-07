package trunk.doi.base.config;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.Request;

public class OkhttpConfig {

    private OkhttpConfig() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Interceptor getBaseHeader = chain -> {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "close")
                .build();
        return chain.proceed(request);
    };


    /**
     * 取消验证HostName
     *
     * @return HostnameVerifier
     */
    public static HostnameVerifier notVerifyHostName() {
        return (hostname, session) -> true;
    }

    /**
     * 取消验证ssl
     *
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory notVerifySSL() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new X509TrustManager[]{getX509TrustManager()}, null);
            return sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取证书X509TrustManager对象
     *
     * @return X509TrustManager
     */
    public static X509TrustManager getX509TrustManager() {


        return new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        };


    }


}
