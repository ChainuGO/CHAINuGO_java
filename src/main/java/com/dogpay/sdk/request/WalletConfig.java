package com.dogpay.sdk.request;

public class WalletConfig {

    private String apiKey;

    private String apiSecret;

    private String platformPubKey;

    private String platformWithdrawPubKey;

    private String servicePriKey;

    private String platformUrl;

    public WalletConfig(String apiKey, String apiSecret, String platformPubKey, String platformWithdrawPubKey,
            String servicePriKey, String platformUrl) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.platformPubKey = platformPubKey;
        this.platformWithdrawPubKey = platformWithdrawPubKey;
        this.servicePriKey = servicePriKey;
        this.platformUrl = platformUrl;
    }

    public WalletConfig(String apiKey, String apiSecret, String platformPubKey, String servicePriKey,
            String platformUrl) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.platformPubKey = platformPubKey;
        this.servicePriKey = servicePriKey;
        this.platformUrl = platformUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getPlatformPubKey() {
        return platformPubKey;
    }

    public void setPlatformPubKey(String platformPubKey) {
        this.platformPubKey = platformPubKey;
    }

    public String getPlatformWithdrawPubKey() {
        return platformWithdrawPubKey;
    }

    public void setPlatformWithdrawPubKey(String platformWithdrawPubKey) {
        this.platformWithdrawPubKey = platformWithdrawPubKey;
    }

    public String getServicePriKey() {
        return servicePriKey;
    }

    public void setServicePriKey(String servicePriKey) {
        this.servicePriKey = servicePriKey;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

}