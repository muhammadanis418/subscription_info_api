package com.subscription.api.subscription_info_api.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subscription.api.subscription_info_api.config.DBSSConfig;
import com.subscription.api.subscription_info_api.misc.Util;
import com.subscription.api.subscription_info_api.model.SubscriptionResponse;
import com.subscription.api.subscription_info_api.model.TokenResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;

@Service("subSer")
@ConfigurationProperties(prefix = "url")
public class SubService {
    private final RestTemplate restTemplate;

    private final DBSSConfig dbssConfig;

    private static final Logger log = LogManager.getLogger(SubService.class);
    @Value("${external.api.url}")
    private String externalApiUrl;

//    @Value("${external.token}")
//    private String token;

    public SubService(RestTemplate restTemplate, DBSSConfig dbssConfig) {
        this.restTemplate = restTemplate;
        this.dbssConfig = dbssConfig;
    }

//    public ResponseEntity<SubscriptionResponse> getSubs(String msisdn) {
//        try {
//            StringBuilder url = new StringBuilder();
//            url.append(externalApiUrl);
//            url.append(msisdn);
//            String finalUrl = url.toString();
//
//            HttpHeaders headers = new HttpHeaders();
//            String token = getJazzTokenToBePassed();
//            log.info("Token = " + token);
//            headers.add("Authorization", token);
//
//            HttpEntity<String> en = new HttpEntity<>(headers);
//
//            ResponseEntity<SubscriptionResponse> subscription = restTemplate.exchange(finalUrl, HttpMethod.GET, en, SubscriptionResponse.class);
//            if (subscription.hasBody()) {
//                return new ResponseEntity<>(subscription.getBody(), subscription.getStatusCode());
//            } else {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//        } catch (HttpClientErrorException ex) {
//            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
//                // Handle 404 Not Found
//                log.info("Subscriber not found: " + msisdn);
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }  catch (Exception ex) {
//            // Handle other exceptions
//            log.error("An unexpected error occurred: " + ex.getMessage(), ex);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    public SubscriptionResponse getSubs(String msisdn) {


        StringBuilder url = new StringBuilder();
        url.append(externalApiUrl);
        url.append(msisdn);
        String finalUrl = url.toString();

        HttpHeaders headers = new HttpHeaders();
        String token = getJazzTokenToBePassed();
        log.info("Token = " + token);
        headers.add("Authorization", token);

        HttpEntity<String> en = new HttpEntity<>(headers);

        try {

            ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.GET, en, String.class);
            log.info("Response is" + response);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SubscriptionResponse subscriptionResponse = null;
            if (response.getStatusCodeValue() >= 200 && response.getStatusCodeValue() <= 220) {
                subscriptionResponse = mapper.readValue(response.getBody(), SubscriptionResponse.class);

                return subscriptionResponse;

            }


        } catch (Exception ex) {
            {
                log.info("No content found for msisdn:" + msisdn);
                log.info(ex.getMessage());
                //  return getDefaultSubscriptionResponse();
            }
        }
        return null;
    }


    private SubscriptionResponse getDefaultSubscriptionResponse() {
        SubscriptionResponse sr = new SubscriptionResponse();
        sr.setOperator("unknown");
        sr.setType("unknown");
        sr.setCode("unknown");
        return sr;
    }

    public String getJazzTokenToBePassed() {
        if (Util.tokenResponse != null && !Util.tokenResponse.getAccess_token().isEmpty()) {
            if (Util.tokenResponse.getExpiry_date().before(new Date())) {
                refreshJazzToken();
            }
        } else {
            refreshJazzToken();
        }
        log.debug("Jazz Token: " + Util.tokenResponse.getToken_type() + " " + Util.tokenResponse.getAccess_token());
        return Util.tokenResponse.getToken_type() + " " + Util.tokenResponse.getAccess_token();
    }

    public void refreshJazzToken() {
        TokenResponse tokenResponse = getJazzToken();
        if (tokenResponse != null && tokenResponse.getAccess_token() != null && !tokenResponse.getAccess_token().isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, Integer.parseInt(tokenResponse.getExpires_in()));
            tokenResponse.setExpiry_date(calendar.getTime());
            Util.tokenResponse = tokenResponse;
            log.info("New Jazz Token Generated: " + Util.tokenResponse.getAccess_token());
        }
    }

    public TokenResponse getJazzToken() {
        log.debug("wso2 v2 getJazzToken");
        try {
//            String url = dbssConfig.getNewBaseURL();
//            String relativeURL = dbssConfig.getApis().get("getToken");

            String url = "https://apimtest.jazz.com.pk:8282/token";
            HttpHeaders headers2 = new HttpHeaders();
            headers2.add("Authorization", "Basic clJkcm5hSnNwakpWc1k0NUhGcWhyMGFfTHVRYTp5Ym9TUXNSNmZRa2lQNFQ4NkI0c2ZUbzJkQ1Vh");
            headers2.add("Content-Type", "application/x-www-form-urlencoded");
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers2);

            log.info("Starts ");

            ResponseEntity<String> response = restTemplate.exchange(new URI(url), HttpMethod.POST, entity, String.class);

            log.info("Api Response" + response);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TokenResponse tokenResponse = null;

            if (response.getStatusCodeValue() >= 200
                    && response.getStatusCodeValue() <= 220) {
                tokenResponse = mapper.readValue(response.getBody(), TokenResponse.class);
                return tokenResponse;
            }


//                ResponseEntity<TokenResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TokenResponse.class);
//                return response.getBody();

        } catch (Exception ex) {
            log.debug("Exception in getJazzToken: " + ex);
        }
        return null;
    }


}
