package trunk.doi.base.https.net;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Author: Othershe
 * Time:  2016/8/11 14:30
 */
public class NetManager {
    private static final int DEFAULT_TIMEOUT = 10;

    public static NetManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final NetManager INSTANCE = new NetManager();
    }

    private NetManager() {
    }

    /**
     * Gosn解析
     *
     * @param service
     * @param <S>
     * @return
     */
    public <S> S create(Class<S> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl(service))
                .build();
        return retrofit.create(service);
    }


    /**
     * 解析接口中的BASE_URL，解决BASE_URL不一致的问题
     *
     * @param service
     * @param <S>
     * @return
     */
    private <S> String getBaseUrl(Class<S> service) {

        try {
            Field field = service.getField("BASE_URL");
            return (String) field.get(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private Interceptor addQueryParameterInterceptor = chain -> {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "close")
                .build();
        return chain.proceed(request);
    };

    /**
     * 返回Okhttp对象
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {

        //配置超时拦截器
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(addQueryParameterInterceptor);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //配置log打印拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor).retryOnConnectionFailure(false).addNetworkInterceptor(loggingInterceptor);
        //无证书
       builder.hostnameVerifier(notVerifyHostName());
        return builder.sslSocketFactory(notVerifySSL(), getX509TrustManager()).build();

        //封装https证书
//        X509TrustManager trustManager;
//        SSLSocketFactory sslSocketFactory;
//        InputStream inputStream = null;
//        try {
//            inputStream = BaseApplication.getInstance().getAssets().open("srca.cer"); // 得到证书的输入流
//            try {
//                trustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                sslContext.init(null, new TrustManager[]{trustManager}, null);
//                sslSocketFactory = sslContext.getSocketFactory();
//
//            } catch (GeneralSecurityException e) {
//                throw new RuntimeException(e);
//            }
//
//            return builder.sslSocketFactory(sslSocketFactory, trustManager).build();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//
//            if (null != inputStream) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//
//                }
//            }
//        }
//        return null;
    }

    /**
     *  取消验证HostName
     * @return
     */
    private HostnameVerifier notVerifyHostName() {
        return (hostname, session) -> true;
    }
    /**
     *  取消验证ssl
     * @return
     */
    private SSLSocketFactory notVerifySSL() {
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
     * @return
     */
    private X509TrustManager getX509TrustManager() {


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


    /**
     * 以流的方式添加信任证书
     */
    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * <p>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }


    /**
     * 添加password
     *
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


}
