package com.dogpay.sdk.response;

public class WalletAddressResponse extends WalletResponse {

    private WalletAddress data;

    public WalletAddress getData() {
        return data;
    }

    public void setData(WalletAddress data) {
        this.data = data;
    }

    public class WalletAddress {

        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

}
