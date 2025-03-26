package com.dogpay.sdk.request;

import java.math.BigDecimal;

public class WithdrawRequest {

    private String openId;

    private Integer tokenId;

    private String addressTo;

    private BigDecimal amount;

    private String safeCheckCode;

    private String callBackURL;

    private String tag;

    public WithdrawRequest(String openId, Integer tokenId, String addressTo, BigDecimal amount, String safeCheckCode) {
        this.openId = openId;
        this.tokenId = tokenId;
        this.addressTo = addressTo;
        this.amount = amount;
        this.safeCheckCode = safeCheckCode;
    }

    public WithdrawRequest(String openId, Integer tokenId, String addressTo, BigDecimal amount, String safeCheckCode,
            String callBackURL, String tag) {
        this.openId = openId;
        this.tokenId = tokenId;
        this.addressTo = addressTo;
        this.amount = amount;
        this.safeCheckCode = safeCheckCode;
        this.callBackURL = callBackURL;
        this.tag = tag;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSafeCheckCode() {
        return safeCheckCode;
    }

    public void setSafeCheckCode(String safeCheckCode) {
        this.safeCheckCode = safeCheckCode;
    }

    public String getCallBackURL() {
        return callBackURL;
    }

    public void setCallBackURL(String callBackURL) {
        this.callBackURL = callBackURL;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}