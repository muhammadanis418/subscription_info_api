package com.subscription.api.subscription_info_api.misc;

import com.subscription.api.subscription_info_api.model.TokenResponse;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static TokenResponse tokenResponse;

    public static final String POSTPAID = "postpaid";
    public static final String PREPAID = "prepaid";
    public static final String HYBRID = "hybrid";
    public static final String TERMINATED = "terminated";
    public static final String INVALID = "invalid";

    public static void ssl() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        SSLContext sc = SSLContext.getInstance("SSL");
        HostnameVerifier hv = (String arg0, SSLSession arg1) -> true;
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

//    public static ActivationRequest getActivationRequest(VasRequest request) {
//
//        ActivationRequest activationRequest = new ActivationRequest();
//        ActivationRequestData data = new ActivationRequestData();
//        data.setId(request.getProductID());
//        data.setType("products");
//        MetaData meta = new MetaData();
//        meta.setChannel(request.getChannel());
//        data.setMeta(meta);
//        activationRequest.setData(data);
//        return activationRequest;
//    }
//
//    public static DeActivationRequest getDeActivationRequest(VasRequest request) {
//        DeActivationRequest deActivationRequest = new DeActivationRequest();
//        ActivationRequestData data = new ActivationRequestData();
//        List<ActivationRequestData> listData = new ArrayList<>();
//        listData.add(data);
//        data.setId(request.getProductID());
//        data.setType("products");
//        MetaData meta = new MetaData();
//        meta.setChannel(request.getChannel());
//        data.setMeta(meta);
//        deActivationRequest.setData(listData);
//        return deActivationRequest;
//    }

}
