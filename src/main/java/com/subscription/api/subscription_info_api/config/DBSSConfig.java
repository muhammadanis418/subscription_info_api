/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.subscription.api.subscription_info_api.config;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aftab
 */
@Component
@ConfigurationProperties(prefix = "dbss")
public class DBSSConfig {

    private Map<String, String> apis;
    private String baseURL;
    private String newBaseURL;
    private List<String> products;

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public Map<String, String> getApis() {
        return apis;
    }

    public void setApis(Map<String, String> apis) {
        this.apis = apis;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getNewBaseURL() {
        return newBaseURL;
    }

    public void setNewBaseURL(String newBaseURL) {
        this.newBaseURL = newBaseURL;
    }

}
