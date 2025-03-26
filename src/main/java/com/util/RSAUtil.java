package com.util;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class RSAUtil {
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;// 设置长度
    private static final String PUBLIC_KEY_NAME = "publicKey";
    private static final String PRIVATE_KEY_NAME = "privateKey";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";

    public static void generatorKey() throws NoSuchAlgorithmException {
        // 获取生成密钥对对象
        KeyPairGenerator rsa = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 生成密钥
        rsa.initialize(1024, new SecureRandom());
        KeyPair keyPair = rsa.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("公钥：" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("私钥：" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
    }

    /**
     * 根据公钥加密 返回base64编码密文
     *
     * @param hash 加密原文
     * @return base64密文
     * @throws Exception 加密异常
     */
    public static String encryptByPub(String hash, String publicKey) throws Exception {
        PublicKey pub = KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        byte[] bytes = cipher.doFinal(hash.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 根据公钥解密 解密base64编码密文
     *
     * @param encode base64密文
     * @return 解密后明文
     * @throws Exception 解密异常
     */
    public static String decryptByPub(String encode, String publicKey) throws Exception {
        PublicKey pub = KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, pub);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encode)));
    }

    /**
     * 根据私钥解密 解密base64编码密文
     *
     * @param encode base64密文
     * @return 解密后明文
     * @throws Exception 解密异常
     */
    public static String decryptByPrv(String encode, String privateKey) throws Exception {
        PrivateKey prv = KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prv);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encode)));
    }

    /**
     * 根据私钥加密 返回base64编码密文
     *
     * @param hash 加密原文
     * @return base64密文
     * @throws Exception 加密异常
     */
    public static String encryptByPrv(String hash, String privateKey) throws Exception {
        PrivateKey prv = KeyFactory.getInstance(KEY_ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, prv);
        byte[] bytes = cipher.doFinal(hash.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);

    }

    /*
     * public static void main(String[] args) throws Exception { // generatorKey();
     * final String PRIVATE_KEY =
     * "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN3cX/oeK9XMwX+reXIVPsgnX5s0uwBJIbvjEX9veJc+vpuTwiwFHWvi9wd4meE0zrlXJCtFtOj8n872XCi/xc35NUz2Qu6RNRggz2oUZZcmgTVa+JWhehYyokXjJx+W05DdFSWcbtU6D4tjKDfyilDH9r8R4wqCjCaLg+J5mrT7AgMBAAECgYEAr2eaIf9UnNYjbbK+c6nEVy558hWAeztWhluB7ATPjJQ40nvBmlf46OEqeSeqUVkUz4fLusKDw2hnWJOCTV1BHpyzhs4ljeFdryVqhXAfGQl+4NYbKqmfXH4nnL1+6qXjTkbb5i7mZKBjIpVoWl8xs08RqI8AJjRWBJVt++jfUHECQQDyHlIJquV4Fy2Kmu4zIqXj+Fx9Gdqp6B8wj7kqmLYNFyxwAav4ylolygP1lWcB+jaEz1xv4SujXhK9qh9N5WAdAkEA6pS5hSmXAvwFMTKuZp6bfE1eZoBjS2USmYGDtqGP2DmR/61YPuXUWERyd3hax2p9ucbGlLkbbuAqxXzy39eN9wJBAK9Gq/U7tqtdfCafSlnSd2212p7mHJpr5Ehvg4kGzKRlZlIfo7OUKfa5bGxZIB0aWmWDCFQmGkVGUSLlCYyGxTECQQDIm/Oj65NXXAtcrYjz/YPe45NteTJxUbh8SoRHdRc8xM8UpjunXokgCzkQ7n5Dt9BZRaKhoUPSqCYrydUylHa1AkAmwkWHJchkxjFGxaCaav26TG/mOfqb1FbF8FrCfN8QDYDLoqduSsysa5C/LnWhOGABZlSydknoqXCTucpn8qe2";
     * final String PUBLIC_KEY =
     * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDd3F/6HivVzMF/q3lyFT7IJ1+bNLsASSG74xF/b3iXPr6bk8IsBR1r4vcHeJnhNM65VyQrRbTo/J/O9lwov8XN+TVM9kLukTUYIM9qFGWXJoE1WviVoXoWMqJF4ycfltOQ3RUlnG7VOg+LYyg38opQx/a/EeMKgowmi4PieZq0+wIDAQAB";
     * String test = encryptByPub("e828835ffe8eb5f8d6eb1dce125c06d0", PUBLIC_KEY);
     * System.out.println("公钥加密" + test); String s = decryptByPrv(test,
     * PRIVATE_KEY); System.out.println("私钥解密" + s);
     * 
     * String ttt = encryptByPrv("e828835ffe8eb5f8d6eb1dce125c06d0", PRIVATE_KEY);
     * System.out.println("私钥加密" + ttt); String sss = decryptByPub(ttt, PUBLIC_KEY);
     * System.out.println("公钥解密" + sss); }
     */

    /**
     * 签名
     * 
     * @param key         私钥
     * @param requestData 请求参数
     * @return
     */
    public static String sign(String key, String requestData) {
        String signature = null;
        byte[] signed = null;
        try {
            PrivateKey privateKey = getPrivateKey(key);

            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            Sign.initSign(privateKey);
            Sign.update(requestData.getBytes(StandardCharsets.UTF_8));
            signed = Sign.sign();

            signature = Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return signature;
    }

    public static String signMd5(String key, String requestData) {
        String signature = null;
        byte[] signed = null;
        try {
            PrivateKey privateKey = getPrivateKey(key);

            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
            Sign.initSign(privateKey);
            Sign.update(requestData.getBytes(StandardCharsets.UTF_8));
            signed = Sign.sign();

            signature = Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return signature;
    }

    /**
     * 解码PrivateKey
     * 
     * @param key
     * @return
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 验签
     * 
     * @param key         公钥
     * @param requestData 请求参数
     * @param signature   签名
     * @return
     */
    public static boolean verify(String key, String requestData, String signature) {
        boolean verifySignSuccess = false;
        try {
            PublicKey publicKey = getPublicKey(key);

            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(publicKey);
            verifySign.update(requestData.getBytes(StandardCharsets.UTF_8));

            verifySignSuccess = verifySign.verify(Base64.getDecoder().decode(signature));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return verifySignSuccess;
    }

    public static boolean verifyMd5(String key, String requestData, String signature) {
        boolean verifySignSuccess = false;
        try {
            PublicKey publicKey = getPublicKey(key);

            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
            verifySign.initVerify(publicKey);
            verifySign.update(requestData.getBytes(StandardCharsets.UTF_8));

            verifySignSuccess = verifySign.verify(Base64.getDecoder().decode(signature));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return verifySignSuccess;
    }

    /**
     * 解码PublicKey
     * 
     * @param key
     * @return
     */
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成公、私钥 根据需要返回String或byte[]类型
     * 
     * @return
     */
    private static Map<String, String> createRSAKeys() {
        Map<String, String> keyPairMap = new HashMap<String, String>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // 获取公、私钥值
            String publicKeyValue = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyValue = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            // 存入
            keyPairMap.put(PUBLIC_KEY_NAME, publicKeyValue);
            keyPairMap.put(PRIVATE_KEY_NAME, privateKeyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keyPairMap;
    }

    public static String composeParams(TreeMap<String, String> params) {
        StringBuilder builder = new StringBuilder();
        Set<Entry<String, String>> entrys = params.entrySet();
        for (Entry<String, String> param : entrys) {
            /** 去掉签名字段 */
            if (param.getKey().equals("signature"))
                continue;

            /** 去掉签名字段 */
            if (param.getKey().equals("sign"))
                continue;

            /** 空参数不参与签名 */
            if (param.getValue() != null) {
                if (builder.length() == 0) {
                    builder.append(param.getKey() + "=" + param.getValue().toString());
                } else {
                    builder.append("&" + param.getKey() + "=" + param.getValue().toString());
                }
            }
        }

        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        /*
         * Map<String, String> keyPairMap = createRSAKeys();
         * System.out.println("生成公、私钥测试："+keyPairMap); String privateKey =
         * keyPairMap.get(PRIVATE_KEY_NAME);
         */

        /*
         * PoolingHttpClientConnectionManager cm = new
         * PoolingHttpClientConnectionManager(); cm.setMaxTotal(20);
         * cm.setDefaultMaxPerRoute(20); CloseableHttpClient httpClient =
         * HttpClients.custom().setConnectionManager(cm).build();
         * 
         * 
         * String privateKey =
         * "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCX7P3uEvBuz8ISQkLbZt7adTVPWKWOCSBHl6C9/wuybzcofTeqTvaKx6l2hTWYDcXzreof5m+JTKDnR9007iz/sv4iWJMmMpN0L2qQ7tN9HBAGxrWhfXta71Y0AbNi2wNLfYSUcNd2ZAP3Cyf3YjAnem1O4yIS5uTHl7sRRt6XqkiuzrHHtbWhL0ciY7t4opySQBm5sIrA9W1YoDdkSavTZTOvWgWYXuNYHqFES2z/QM13c5Jg44UURw12ZfYJEj+rFygxOvl4kpy/tNQI7hQL9ETuYjSgwRBwlcJc5xND+o6H1qQTG4A2FH7bbh/8bxu9I9X1KYOnBr8Skxj4Y/fDAgMBAAECggEBAJEVt+I1n/qS2WkKckKIWOYS7Jgbte6QdI0qFk2bVRZP80LtYc14nyUlS4Zt7CKLdLEjMuTH8VgTH83+6Rb1JXADBYLTR6NAxWQ9K+3AxaNCu2NdPHM+HCFZUt9TiHYtZVYgRg5kPL9JAChaNQ6gbBAncF/XtOCMM4sQMYtsPIJkmOFKuCkgIy+/NZm1IIC/lprfdhQtGzfb6djZc4oTwIW/2dhZr1xFmafTH2bW/MENKgtL8wNNZrWkk9ViHgVuw+SA1DkdV4BbbwHRHRtgpEVD3FHnwT8IWe4b9q2dmmD518U3iUzmrS0o8VVPl8wJU6saqK4weyXzfutuSqWe3tECgYEAz3JUQNxmc+i1pTktNLVfmWABuLYjmG+mQhi4gpQZjYWKgyhX3B52RQ1vWyDEPTXzG07bb7A3HANpqv4lZnlIvwfq6VvCF5UwxqMCLimz32Ep4osY3GHWd19Db2ejQRl+bQGvgltu03oFHXKe5aRvyQT+kOF8XASipmDKyzqQvf8CgYEAu3v+xmiYsah/G6lHCt/DWskc15p45eUyM78NlvjIoSg6v9+mEpQXXx9U67vJVnspT8qbEjWApLUYm3DKo0dzJCHuNTjDfxUZ1QLBlyLuplr2W4ZwWIVMBtGRyXt+UDRNBe8p6TpHuVuwwHf28N2mVY5n9cs2/Jf7gqGcwdv9Tj0CgYAHSI9Wu6ZJOYlvJKPGrvuNSgalcperWXgv5+hKhWKkSmUDl94F13f1krQvMeZR89v2QH2J3VkB/ciu/T9Y6rc4JdIEoXkeFNnij4SbFFHBbQroblPQIQtDo6+dcLkVpwx+TUnZPNaWTDlzwDt9+pkGKMu7UH9qR6tiRhG4svS0BwKBgA7VO430/oenvg6xMNayZ2FKYgbIyOHfZT5k0i1sl2rwpN1DU39KyiKTdwODguVdryIJ9ITXSMs/LKg3n0CVyY1FRfRd/lGSmM2afzh24gy1S6JoG4FVYnNzpyYaqB6uVJ2wk09Jdh07HNvNYfMihiNkteLjZgFMAPEP2jgYMI09AoGBALUCjw5fyvObfdkQHcaroII/rXrZ0SjU01r4c6Y9WkMeQWcQdayFxhUPRutD/F1FcaYNZpLu/Zu18g1NYCxgqayC+b2cYNGpjJ/oUOtHzJ+Bmy6cMys9U9OvJgKb5hwxn0tvvVJ4huZfjq+3OMJLdbRfKX/OsxOQbtfV8pa8n5nm";
         * 
         * Map<String,Object> paramMap = new TreeMap(); paramMap.put("uid", 10011);
         * paramMap.put("transfer_type", "out"); paramMap.put("out_transfer_no",
         * "10012"); paramMap.put("coin_code", "USDT"); paramMap.put("vol", "20");
         * paramMap.put("timestamp", new Date().getTime()/1000+"");
         * 
         * StringBuilder stringBuilder = new StringBuilder(); for(String key :
         * paramMap.keySet()){ stringBuilder.append(key+paramMap.get(key)); }
         * 
         * String sign = sign(privateKey, stringBuilder.toString());
         * paramMap.put("sign", sign);
         * 
         * HttpPost httpPost = new
         * HttpPost("http://coapi.mybts.info/contract/transferFunds");
         * httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
         * httpPost.setEntity(new StringEntity(JSON.toJSONString(paramMap),"UTF-8"));
         * System.out.println(JSON.toJSONString(paramMap));
         * 
         * CloseableHttpResponse response = httpClient.execute(httpPost); InputStream in
         * = response.getEntity().getContent();
         * System.out.println(IOUtils.toString(in));
         */

        Map<String, String> keyPairMap = createRSAKeys();
        System.out.println("生成公、私钥测试：" + keyPairMap);
    }

}
