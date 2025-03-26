package com.dogpay;

import java.math.BigDecimal;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dogpay.sdk.DogPayWallet;
import com.dogpay.sdk.request.WalletConfig;
import com.dogpay.sdk.request.WithdrawRequest;
import com.dogpay.sdk.response.NotifyResponse;
import com.google.gson.Gson;
import com.util.RSAUtil;
import com.util.StringUtil;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {

//        String servicePriKey = "xxx";
//        String apiKey = "xxx";
//        String apiSecret = "xxx";
//        String platformPubKey = "xxx";
//        String platformWithdrawPubKey = "xxx";
//        String platformUrl = "https://testnet.chainugo.com/sdk/";

//        WalletConfig config = new WalletConfig(apiKey, apiSecret, platformPubKey, platformWithdrawPubKey, servicePriKey,
//                platformUrl);
//        DogPayWallet wallet = new DogPayWallet();

        // 建议采用平台统一前缀（如合作伙伴：HASH）+用户唯一号码 构成用户的唯一 OpenId
//        String openId = "zero_00000000001";

        /**
         * ---------------------------- 用户创建 ----------------------------
         */
//        wallet.createUser(config, openId);

//        2024-08-20T00:43:33.193680 get url: https://vapi.dogpay.ai/sdk/user/create
//        2024-08-20T00:43:33.215027post body: {"OpenId":"zero_00000000001"}
//        2024-08-20T00:43:36.098786post body: {"sign":"wdeqphCtdz.....","timestamp":"1724085816054","data":{"OpenId":"zero_00000000001"},"code":1,"msg":"ok"}

        /**
         * ---------------------------- 钱包创建----------------------------
         */
//        wallet.createWallet(config, "1", openId);

//        2024-08-20T00:46:47.942551 get url: https://vapi.dogpay.ai/sdk/wallet/create
//        2024-08-20T00:46:47.962614post body: {"ChainID":"1","OpenId":"zero_00000000001"}
//        2024-08-20T00:46:49.633622post body: {"sign":"XvnzqOTayrQ.....","timestamp":"1724086009575","data":{"address":"0x9fdeb9f1603d7a5427a34bc5b4373d816355c068","UserId":xxxxx,"PartnerId":xxx,"ChainID":1,"OpenId":"zero_00000000001"},"code":1,"msg":"ok"}

        /**
         * ---------------------------- 提现 ----------------------------
         */
//        Integer tokenId = 1;
//        String addressTo = "0x9702208416078999e54E0FcF3501766B443d8021";
//        BigDecimal amount = new BigDecimal("0.001");
//        // 用户提现交易的安全验证码
//        String safeCheckCode = "test_order_00000000001";
//        WithdrawRequest req = new WithdrawRequest(openId, tokenId, addressTo, amount, safeCheckCode);
//        wallet.userWithdrawByOpenID(config, req);

//        2024-08-20T00:54:36.178135 get url: https://vapi.dogpay.ai/sdk/partner/UserWithdrawByOpenID
//        2024-08-20T00:54:36.195964post body: {"AddressTo":"0x9702208416078999e54E0FcF3501766B443d8021","Amount":"0.001","OpenId":"zero_00000000001","SafeCheckCode":"test_order_00000000001","TokenId":"1"}
//        2024-08-20T00:54:39.237337post body: {"sign":"","timestamp":"1724086479297","data":null,"code":0,"msg":"Insufficient pay wallet transaction fee."}

        /**
         * ---------------------------- 接收充值或者提现通知回调验证处理 ----------------------------
         */

//        TreeMap<String, String> params = new TreeMap<>();
        /**
         * 1 表示充值交易； 2 表示提现交
         */
//        if (!params.containsKey("type") || StringUtil.isNullOrEmpty(params.get("type")))
//            return NotifyResponse.fail("type empty");

        /**
         * 
         * 交易状态：
         * 
         * 1 交易（提现或审核）成功； -1 交易链上失败 -2 表示提现审核拒绝 2 等待审核中； 3 表示链上处理中；
         * 
         */
//        if (!params.containsKey("status") || StringUtil.isNullOrEmpty(params.get("status")))
//            return NotifyResponse.fail("status empty");

        // 常规通知验证签名 (true && false)
//        Boolean status = wallet.dataSignVerify(config, params);

        // true
        // 业务正常逻辑处理

        // 返回json
//        NotifyResponse.ok("ok");

        /**
         * ---------------------------- 接收处理提现订单二次复核回调验证 ----------------------------
         */
        // 签名验证通过之后同时进行私钥数据签名
//        params.put("amount", "xxxx");
//        params.put("openId", "xxxx");
//        params.put("safeCode", "xxxx");
//        params.put("sign", "xxxx");
//        params.put("timestamp", "xxx");
//        params.put("toAddress", "xxx");
//        params.put("tokenId", "4");
//        String sign = wallet.withdrawalOrderCheck(config, params);
        // 业务正常逻辑处理

        // 返回json
    }

}
