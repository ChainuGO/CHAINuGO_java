package com.dogpay.sdk;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.dogpay.sdk.request.WalletConfig;
import com.dogpay.sdk.request.WithdrawRequest;
import com.dogpay.sdk.response.WalletAddressResponse;
import com.dogpay.sdk.response.WalletUserResponse;
import com.google.gson.Gson;
import com.util.HttpClientUtil;
import com.util.MD5Util;
import com.util.RSAUtil;
import com.util.StringUtil;

public class DogPayWallet {

    private String request(String path, String method, TreeMap<String, String> params, WalletConfig config) {
        try {
            String timestamp = Long.toString(System.currentTimeMillis());
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("key", config.getApiKey());
            headers.put("sign", getSign(config, params, timestamp));
            headers.put("timestamp", timestamp);
            headers.put("clientSign", getClientSign(config, params));
            return HttpClientUtil.getInstance().request(config.getPlatformUrl() + path, method, params, headers);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /** 拼接签名字符串，md5签名 */
    private String getSign(WalletConfig config, Map<String, String> data, String timestamp) {
        TreeMap<String, String> params = new TreeMap<String, String>(data);
        return MD5Util.getMD5(config.getApiSecret() + RSAUtil.composeParams(params) + timestamp);
    }

    /** 拼接签名字符串，RSA签名 MD5withRSA */
    private String getClientSign(WalletConfig config, Map<String, String> data) {
        TreeMap<String, String> params = new TreeMap<String, String>(data);
        return RSAUtil.signMd5(config.getServicePriKey(), RSAUtil.composeParams(params));
    }

    public WalletUserResponse createUser(WalletConfig config, String openId) {

        TreeMap<String, String> params = new TreeMap<>();
        params.put("OpenId", openId);
        String body = request("user/create", "POST", params, config);
        if (StringUtil.isNullOrEmpty(body))
            return null;

        Gson gson = new Gson();
        return gson.fromJson(body, WalletUserResponse.class);
    }

    public String createWallet(WalletConfig config, String chainId, String openId) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("ChainID", chainId);
        params.put("OpenId", openId);
        String body = request("wallet/create", "POST", params, config);
        if (StringUtil.isNullOrEmpty(body))
            return null;

        Gson gson = new Gson();
        WalletAddressResponse response = gson.fromJson(body, WalletAddressResponse.class);
        if (response.getCode() == 1)
            return response.getData().getAddress();

        return null;
    }

    public String userWithdrawByOpenID(WalletConfig config, WithdrawRequest req) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("OpenId", req.getOpenId());
        params.put("AddressTo", req.getAddressTo());
        params.put("SafeCheckCode", req.getSafeCheckCode());
        params.put("TokenId", req.getTokenId().toString());
        params.put("Amount", req.getAmount().stripTrailingZeros().toString());

        if (req.getTag() != null)
            params.put("tag", req.getTag());

        if (req.getCallBackURL() != null)
            params.put("callBackURL", req.getCallBackURL());

        String body = request("partner/UserWithdrawByOpenID", "POST", params, config);
        return body;
    }

    public Boolean dataSignVerify(WalletConfig config, TreeMap<String, String> params) {
        if (!RSAUtil.verifyMd5(config.getPlatformPubKey(), RSAUtil.composeParams(params), params.get("sign"))) {
            return false;
        }
        return true;
    }

    public String withdrawalOrderCheck(WalletConfig config, TreeMap<String, String> params) {
        if (!RSAUtil.verifyMd5(config.getPlatformWithdrawPubKey(), RSAUtil.composeParams(params), params.get("sign"))) {
            return null;
        }
        return getClientSign(config, params);
    }

}
