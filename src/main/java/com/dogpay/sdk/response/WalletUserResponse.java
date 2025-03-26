package com.dogpay.sdk.response;

public class WalletUserResponse extends WalletResponse {

    private WalletUser data;

    public WalletUser getData() {
        return data;
    }

    public void setData(WalletUser data) {
        this.data = data;
    }

    public class WalletUser {

        private String OpenId;

        public String getOpenId() {
            return OpenId;
        }

        public void setOpenId(String openId) {
            OpenId = openId;
        }

    }

}
