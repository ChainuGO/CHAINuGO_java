# ChainuGO SDK

![ChainuGO](./doc/images/ChainuGO.png)

[English](README.md) | 简体中文

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 1. 欢迎使用 chain pay sdk

### 1.1. 准备接入

1. 合作伙伴

唯一标识验证合作伙伴身份，不同的合作伙伴配置不同参数，新合作伙伴可以联系平台。

正式环境管理平台：

[https://admin.chainugo.com/](https://admin.chainugo.com/)

沙盒环境管理平台：

[https://testnet.chainugo.com/](https://testnet.chainugo.com/)

获取 API 密钥：

![system-apikey](./doc/images/system-apikey.png)

平台RSA公钥：

![system-apikey-plat-pub](./doc/images/system-apikey-plat-pub.jpg)

平台风控 RSA 公钥：

![system-apikey-risk-pub](./doc/images/system-apikey-risk-pub.jpg)

回调地址需要开通 ip 白名单：

![system-whitelist](./doc/images/system-whitelist.png)

查看交易列表：

![transaction-list](./doc/images/transaction-list.png)


设置callback地址：

![system-callback-](./doc/images/system-callback.png)


1. 提供需要支持的主链与合约币种清单

合约币需要合约地址、所属主链、精度、名称等基本信息给到业务对接人。

3. 接口沙盒环境调试

接口沙盒环境对接，联调，演练。 接口采用 `RSA2048` 位 双向签名验签形式确保通信可信性。 商户需要提前将`公钥`给到平台，用于平台对商户请求参数验签。 商户需要提前获取`平台公钥`，用于对平台响应结果验签。

### 1.2. 签名规则

* 采用`md5`算法将商户的 secret码、时间戳（单位：毫秒）和传输数据生成 sign 签名 ；
* 采用`RSA`算法将传输数据生成 clientSign 签名；

#### 1.2.1. sign 签名

1. 注册合作伙伴，获取 Key 和 Secret
2. 解析请求的 JSON Body，按照 JSON 中的 key 按照 ASCII 从小到大排序，拼接字符串 dataStr=key1=value1&key2=value2&key3=value3&...
3. 生成 timestamp（单位：毫秒）
4. 加密生成 sign：
   
   加密前的明文：strToHash = SecretKey+dataStr+timestamp

   对上面的明文 strToHash 进行 MD5 加密生成 sign
5. 将 key，timestamp, sign 放在 http head

#### 1.2.2. clientSign 签名

创建私钥

```bash
openssl genpkey -algorithm RSA -out rsa_private_key.pem
```

根据私钥创建公钥

```bash
openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
```

签名算法详解

1. 获取请求参数并进行格式化获得一个新的参数格式化后的字符串：
2. 对第一步data进行用RSA私钥签名并把签名结果保存到一个变量中：

生成签名字符串, 对于如下的参数数组： `user_id = 1 coin = eth address = 0x038B8E7406dED2Be112B6c7E4681Df5316957cad amount = 10.001 trade_id = 20220131012030274786`

对数组里的每一个键从a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推。排序完成之后，再把所有数组值以“&”字符连接起来，如$dataString：

`address=0x038B8E7406dED2Be112B6c7E4681Df5316957cad&amount=10.001&coin=eth&trade_id=20220131012030274786&user_id=1`

这串字符串便是拼接好的字符串。

使用私钥将数据做`RSA-md5`的签名。

### 1.3. 公共信息说明

以下信息对所有接口均通用、后面每个接口不重复描述

#### 1.3.1. 生产环境

API地址： 

[https://api.chainugo.com/sdk/](https://api.chainugo.com/sdk/)

#### 1.3.2. 沙盒环境

API地址： 

[https://testnet.chainugo.com/sdk/](https://testnet.chainugo.com/sdk/)

#### 1.3.3. 请求公共参数

请求头部定义

| 参数名     | 约束      | 案例                               | 说明                           |
| :--------- | :-------- | :--------------------------------- | :----------------------------- |
| key        | 长度：64  | ithujj3onrzbgw5t                   | 合作伙伴key                    |
| timestamp  | 长度：32  | 1722586649000                      | 发起请求的时间戳（单位：毫秒） |
| sign       | 长度：32  | 9e0ccfe3915e94bcc5bf7dd51ad4e8d9   | 合作伙伴secret签名             |
| clientSign | 长度：512 | 9e0ccfe3915e94bcc5bfbBsC5EUxV6 ... | 合作伙伴RSA签名                |

#### 1.3.4. 公共信息须知

| 名称       | 类型      | 案例                               | 描述                               |
| :--------- | :-------- | :--------------------------------- | :--------------------------------- |
| 全局状态码 | integer   | 1                                  | 1 表示成功，详细见 全局状态码      |
| 信息       | string    | ok                                 | 返回文本方式的信息                 |
| 数据       | json      | {"OpenID":"HEX..."}                | 返回具体数据内容                   |
| 时间       | timeStamp | 1722587274000                      | UTC 时间统一时间不带时区，单位毫秒 |
| 签名       | sign      | 9e0ccfe3915e94bcc5bfbBsC5EUxV6 ... | 平台使用RSA对全部数据签名数据      |

## 2. 安装

```bash
```

## 3. 业务流程图

### 3.1. 充值流程

![pay-service-flow](./doc/images/pay-service-flow.png)

### 3.2. 提现流程

![pay-service-withdraw-flow](./doc/images/pay-service-withdraw-flow.png)

## 4. 用户管理

### 4.1. 注册新用户

* 功能：创建新的平台用户，需要传递用户唯一 ID 标识

#### HTTP Request

> POST ： `/user/create`

#### 请求参数

| 参数名 | 必选 | 类型   | 说明                                                                        |
| :----- | :--- | :----- | :-------------------------------------------------------------------------- |
| OpenId | 是   | string | 建议采用平台统一前缀（如合作伙伴：HASH）+用户唯一号码 构成用户的唯一 OpenId |

#### 返回参数说明

| 参数名      | 类型   | 说明               |
| :---------- | :----- | :----------------- |
| code        | int    | 全局状态码         |
| msg         | string | 状态描述           |
| data.OpenId | string | 返回用户唯一OpenId |
| sign        | string | 平台签名           |

### 4.2. 注册钱包

* 功能：创建用户对应这个区块链网络的钱包账号
* 前置条件：指定 OpenId 的用户已经创建成功

#### HTTP Request

> POST ： `/wallet/create`

#### 请求参数

| 参数名  | 必选 | 类型   | 说明              |
| :------ | :--- | :----- | :---------------- |
| ChainID | 是   | string | 公链ID            |
| OpenId  | 是   | string | 用户的唯一 OpenId |

#### 返回参数说明

| 参数名       | 类型   | 说明              |
| :----------- | :----- | :---------------- |
| code         | int    | 全局状态码        |
| msg          | string | 状态描述          |
| data.address | string | 钱包地址          |
| data.UserId  | string | 用户编号          |
| data.ChainID | string | 公链ID            |
| data.OpenId  | string | 用户的唯一 OpenId |
| sign         | string | 平台签名          |

### 4.3. 注册热钱包

* 功能：创建商户对应这个区块链网络的钱包账号，整个商户只有这一个热钱包，用户充值指定的金额来确定来至哪个订单
* 前置条件：无

#### HTTP Request

生产环境`API`地址：[https://api.chainugo.com/sdk/](https://api.chainugo.com/sdk/)

沙盒环境`API`地址：[https://testnet.chainugo.com/sdk/](https://testnet.chainugo.com/sdk/)

> POST ： `/user/order/create`

#### 请求参数

| 参数名  | 必选 | 类型   | 说明              |
| :------ | :--- | :----- | :---------------- |
| ChainTokenId | 是   | string | 链ID对应币ID唯一标识 |
| OrderID  | 是   | string | 用户的唯一 OrderID |
| Amount  | 是   | string | 用户的充值金额，只能整数 |

Token 类型

| TokenID | Value         | Description                      |
| :------ | :------------ | :------------------------------- |
| 1       | ETH-ETH       | ETH Network ETH                     |
| 2       | ETH-USDT      | ETH Network USDT                    |
| 3       | ETH-USDC      | ETH Network USDC                    |
| 4       | TRON-TRX      | TRON Network TRX                    |
| 5       | TRON-USDT     | TRON Network token：USDT            |
| 6       | BNB-BNB       | BNB Smart Chain Network BNB         |
| 7       | BNB-USDT      | BNB Smart Chain Network token：USDT |
| 8       | BNB-USDC      | BNB Smart Chain Network token：USDC |
| 9       | ARB-ETH       | Arbitrum One Network token：ETH     |
| 10       | ARB-USDT      | Arbitrum One Network token：USDT   |
| 11       | ARB-USDC      | Arbitrum One Network token：USDC   |
|12	       |ARB-DAI        | Arbitrum One Network token：DAI    |
|13	       |ETH-DAI        | ETH Network DAI                    |
|14        |TRON-USDD      |TRON  Network  USDD                 |
|15	       |BNB-ETH        |BNB  Network  ETH                   |
|16	       |BNB-DAI        |BNB  Network  DAI                    |
|17	       |Optimism-ETH   |Optimism  Network  ETH              |
|18        |Optimism-USDT  |Optimism  Network  USDT             |
|19	       |Optimism-USDC  |Optimism  Network  USDC             |
|20	       |Polygon-MATIC  |Polygon  Network  MATIC             |
|21	       |Polygon-USDT   |Polygon  Network  USDT              |
|22	       |Polygon-USDC   |Polygon  Network  USDC              |
|23	       |Base-ETH       |Base  Network  ETH                  |
|24	       |Base-USDC      |Base  Network  ETH                  |
|25	       |AVAX-AVAX      |AVAX  Network  AVAX                 |
|26	       |AVAX-USDT      |AVAX  Network  USDT                 |
|27	       |AVAX-USDC      |AVAX  Network  USDC                 |
|28	       |OpBNB-BNB      |OpBNB  Network  BNB                 |
|29	       |OpBNB-USDT     |OpBNB  Network  USDT                |
|30        |TRON-USDC      |TRON  Network  USDC                 |
|31        |BNB-BTCB       |BNB  Network  BTCB                  |

请求实例：

```bash
curl --location 'https://testnet.chainugo.com/sdk/user/order/create' \
--header 'key: vratson2i5hjxgkd' \
--header 'sign: 0592dc64d480fb119d1e07ce06011db8' \
--header 'clientSign: xxxxxxxxxxxxxxxxx' \
--header 'Content-Type: application/json' \
--header 'timestamp: 1725076567682' \
--data '{
  "OrderID":"PT00001",
  "ChainTokenId":"7",
  "Amount":"10"
}'
```

key： 合作伙伴的Key

sign： 生成规则为`md5( 合作伙伴的Secret + "发送数据key=value，中间使用&连接" + 当前时间戳单位为毫秒转换成字符串 )`

例子： md5("mysecret"+"ChainId=1&OpenId=PT00001"+"1725076567682")

clientSign： 生成规则为 `rsaWithMd5( "发送数据key=value，中间使用&连接" )`

timestamp： 当前时间戳单位为毫秒转换成字符串

#### 返回参数说明

| 参数名            | 类型   | 说明               |
| :-----------     | :----- | :----------------  |
| code             | int    | 全局状态码          |
| msg              | string | 状态描述            |
| data.payUrl      | string | 调用的url地址       |
| data.platOrderId | string | 平台订单编号        |
| data.orderId     | string | 用户订单编号        |
| data.amount      | float  | 用户申请金额        |
| data.platAmount  | float  | 平台确认金额        |
| data.addr        | string | 充值地址            |
| data.token       | string | 充值币种            |
| data.status      | int32  | 订单状态：0=等待充值，1=充值成功，2=充值失败 |
| data.endTime     | int64  | 订单结束时间        |
| sign             | string | 平台签名            |

返回实例

```json
{
    "sign": "i24t857ix3027CPiuQ+getyC7u3pJHcL/m5NiPUQwmv5XkOEdrDnckoblGXIbdO2hgjpJDg47Lbq/YoKu+NiJHGJTwu10CAYDRzyiimBfLsP9yNdnFxJLTUEfOKPSXupJdceMZL8WXF4XkMpwHCrUqhekyM+aVLDHsfROKf3uP+zdjJ++9Z//3Xukg57OBvspYGPqpgIY5fOmALiXs3DgZTdXRYYN6MBRUR3NEd1lb4dSO1AjAGkahhIjGqwaeqSO6YAcfwoj9Be48QS9CurfVxZ9xM8FvbPzPsa2W8kHG7q+Cji4NTk243LJyrQ9QFRpTDUTo5JNrJ1vne/2js8kg==",
    "timestamp": "1725432397796",
    "data": {
       "payUrl": "https://tpay.chainugo.com/#/?orderId=1744634767162970DbVyK&key=411e0fee1622f47afe5c4e20750a3bb5",
        "platOrderId": "1744634767162970DbVyK",
        "orderId": "1744634767162970DbVyK",
        "amount": 1,
        "platAmount": 1.881,
        "addr": "0x971cf1e196aaF9AB43c1FC491E1bAE945269b3E6",
        "token": "USDT",
        "status": 0,
        "endTime": 1744636567
    },
    "msg": "ok",
    "code": 1
}
```

## 5. 提现

### 5.1. 合作伙伴用户提现

* 功能：提供合作伙伴用户提现的操作接口，需要从合作伙伴对应 Token 提现资金池的账号提现转账给用户设置的提现钱包地址，对于合作伙伴可以设置一个安全回调地址校验提现合法性，校验合法的情况下，直接操作商户资金池钱包完成提现。
* 提现交易接口会检查默认出款热钱包有充足的钱包手续费和提现资产；
* 提现接口默认会依据 安全验证码作为提现交易的唯一性参数要求，建议一般以业务平台的提现唯一订单号作为安全码使用， 重复的安全验证码提交会返回错误编码；
* 所有的提现交易请求会匹配在通道平台配置的风控审核规则，参数请求合法的情况下，交易请求会被接收，满足自动审核规则的交易会立即提交网络，返回提交交易的Hash信息（返回字段 data）；对于需要在通道二次审核的提现交易请求，返回（code=2），不需要再次提交提现请求，需要管理员去通道平台完成二次审核，完成二次审核后，交易订单会回调通知提现交易请求的状态变化。
* 前置条件：对应货币的资金池拥有超过提现额度的数量（特别注意 ETH 网络 Token 提现，要求资金池钱包有一定的 ETH 手续费余额）
* ⚠️ 注意：**对于发起区块链的提现操作行为，需要确保前置审核流程完成后才需要调用接口，一旦发起区块链交易，无法撤销或退回。**

#### HTTP Request

> POST ： `/partner/UserWithdrawByOpenID`

#### 请求参数

| 参数名        | 必选 | 类型   | 说明                                                       |
| :------------ | :--- | :----- | :--------------------------------------------------------- |
| OpenId        | 是   | string | 用户的唯一 OpenId                                          |
| TokenId       | 是   | string | TokenId                                                    |
| Amount        | 是   | float  | 用户提现的货币数量，精确到 2 位小数                        |
| AddressTo     | 是   | string | 提现目标钱包                                               |
| CallBackUrl   | 否   | string | 回调通知用户提现的进度，可不设置，采用合作伙伴默认回调 url |
| SafeCheckCode | 否   | string | 用户提现交易的安全验证码                                   |

#### 返回参数说明

| 参数名 | 类型   | 说明                                                                                                                                                                                                                                                                                                                        |
| :----- | :----- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| code   | int    | 状态码</br>0 参数错误，或订单号重复，或提现地址格式错误，或出款钱包手续费不足，msg可查看详细信息；</br>1 提现交易成功提交，已经提交区块链网络， data中有提交的交易唯一hash；</br>2 提现交易成功提交，需要通道二次审核后才能继续完成交易，完成审核处理后通过回调来更新交易信息；</br>-1 提现交易失败，可以重新提交提现请求； |
| msg    | string | 状态描述                                                                                                                                                                                                                                                                                                                    |
| data   | string | 交易的hash                                                                                                                                                                                                                                                                                                                  |
| sign   | string | 平台签名                                                                                                                                                                                                                                                                                                                    |

### 5.2. 查询用户充提记录

* 功能：提供合作伙伴查询用户充值/提现记录的数据接口，支持分页查询数据
* 前置条件：无

#### HTTP Request

> POST ： `/wallet/GetPayChargeRecords`

#### 请求参数

| 参数名   | 必选 | 类型   | 说明                                                                        |
| :------- | :--- | :----- | :-------------------------------------------------------------------------- |
| OpenId   | 是   | string | 用户的唯一 OpenId                                                           |
| TopStart | 是   | int    | 当前记录起始序号，客户端自定义页面数量和计算起始终止序号                    |
| TopEnd   | 是   | int    | 当前记录结束序号                                                            |
| PayType  | 是   | int    | 过滤类型，0 表示不过滤，1 表示只查询提现记录；2 表示只查询充值              |
| hashCode | 否   | string | 可以为空，指定 Hash 查询交易记录                                            |
| safeCode | 否   | string | 可以为空，指定 safeCode，一般提现可以通过唯一提现安全验证码查询提现记录状态 |

#### 返回参数说明

| 参数名                                  | 类型     | 说明                                                                                                                                                                          |
| :-------------------------------------- | :------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| code                                    | int      | 全局状态码                                                                                                                                                                    |
| msg                                     | string   | 状态描述                                                                                                                                                                      |
| data.userPayChargeRecords[]             | object[] | 充值提现交易数据集合                                                                                                                                                          |
| data.userPayChargeRecords.OpenID        | string   | 用户的唯一 OpenId                                                                                                                                                             |
| data.userPayChargeRecords.PayOrCharge   | int      | 1 标识充值，2 标识提现                                                                                                                                                        |
| data.userPayChargeRecords.FromAddress   | string   | 发起地址                                                                                                                                                                      |
| data.userPayChargeRecords.ToAddress     | string   | 收款地址                                                                                                                                                                      |
| data.userPayChargeRecords.HashCode      | string   | 区块链交易 Hash                                                                                                                                                               |
| data.userPayChargeRecords.SafeCode      | string   | 指定safeCode                                                                                                                                                                  |
| data.userPayChargeRecords.Amount        | float    | 金额：精确到 2 位小数                                                                                                                                                         |
| data.userPayChargeRecords.Status        | int      | 交易状态，</br>1 成功完成，</br>2 等待审核，</br>3 等待区块交易确认</br>-1 表示交易失败（业务平台需要回滚用户资产）</br>-2 表示审核拒绝或交易取消（业务平台需要回滚用户资产） |
| data.userPayChargeRecords.NoticeTimes   | int      | 区块确认次数                                                                                                                                                                  |
| data.userPayChargeRecords.NoticeUrl     | string   | 回调地址                                                                                                                                                                      |
| data.userPayChargeRecords.NoticeRespone | string   | 回调反馈                                                                                                                                                                      |
| data.userPayChargeRecords.CreateTime    | string   | 创建时间                                                                                                                                                                      |
| data.TotalCount                         | int      | 交易总数量，提供分页查询数据                                                                                                                                                  |
| sign                                    | string   | 平台签名                                                                                                                                                                      |

## 6. 通知回调

充值/提现交易 回调接口说明

1. 充值和提现的交易会多次重复通知回调，以最后一次收到回调通知的交易信息和状态为准；
2. 要求业务端返回有效的回调信息，格式参照返回参数说明，业务端需要返回code=0表示经处理完成回调消息，不需要继续通知，否则回调会持续通知（初始50次每隔2秒，后续每隔10分钟一次）直至返回 code=0的消息确认；

### 6.1. Token 转账回调通知

> POST 

* 功能：定义平台给应用方接收 Token 转账通知的回调消息格式说明，适应于应用方针对 Token 交易状态（提现或充值）的事件通知消息处理应用方依据应用功能设计可选支持回调通知接口功能

#### 请求参数

| 参数名       | 必选 | 类型   | 说明                                                                                                                            |
| :----------- | :--- | :----- | :------------------------------------------------------------------------------------------------------------------------------ |
| status       | 是   | int    | 交易状态：</br>1 交易（提现或审核）成功；</br>-1 交易链上失败</br>-2 表示提现审核拒绝</br>2 等待审核中；</br>3 表示链上处理中； |
| type         | 是   | int    | 1 表示充值交易； 2 表示提现交易                                                                                                 |
| hash         | 是   | string | 交易 Hash 值                                                                                                                    |
| confirm      | 是   | int    | 交易在链上完成的确认次数                                                                                                        |
| createdtime  | 是   | string | 创建时间                                                                                                                        |
| from         | 是   | string | 交易发起方地址                                                                                                                  |
| to           | 是   | string | 交易接收地址                                                                                                                    |
| amount       | 是   | string | 交易数量                                                                                                                        |
| chainid      | 是   | string | 交易链 id                                                                                                                       |
| tokenid      | 是   | string | 交易 tokenid                                                                                                                    |
| tokenaddress | 是   | string | 交易 token 合约地址                                                                                                             |
| safecode     | 是   | string | 对提现订单有效，一般为提现订单的唯一编号 orderid                                                                                |
| timestamp    | 是   | string | 交易时间戳                                                                                                                      |
| tag          | 否   | string | 可选，针对 XRP，EOS                                                                                                             |
| sign         | 是   | string | 平台签名数据 **接收方可以用 平台公钥 验证平台返回数据的可靠性，强烈建议接收方验证签名有效性**                                   |

### 6.2. 提现订单二次复核

* 功能：商户提现订单风控二次复核接口
* ⚠️ 注意：**平台 给商户分配单独的风控RSA公钥（与充值/提现回调通知公钥不同）**
* 触发时机：当管理员在商户端（系统设置）配置风控回调URL 参数后，通道将对每笔发起的提现交易接口请求增加额外的风控回调二次复核，只有商户端风控URL返回正确的验证通过code，交易才能有效提交。
* 技术要求：需要商户端技术实现并配置二次复核回调接口。

#### HTTP Request

> POST ： `/withdrawal/order/check`

#### 请求参数

| 参数名    | 必选 | 类型   | 说明                                                                       |
| :-------- | :--- | :----- | :------------------------------------------------------------------------- |
| safeCode  | 否   | string | 商户端提交交易唯一ID，一般对应商户端提现订单ID（提现交易的SafeCheckCode ） |
| openId    | 是   | string | 商户端提交提现交易的所属用户ID                                             |
| tokenId   | 是   | string | 币种ID，以平台提供的币种ID为准                                             |
| toAddress | 是   | string | 提现地址                                                                   |
| amount    | 是   | string | 提现数量                                                                   |
| timestamp | 是   | int    | 当前时间戳                                                                 |
| sign      | 是   | string | 签名，只对data中的参数进行加签；需要用平台的风控RSA公钥验证签名正确性      |

#### 返回参数说明

| 参数名    | 类型   | 说明                                                   |
| :-------- | :----- | :----------------------------------------------------- |
| code      | int    | 复核检查结果，0表示检查通过；其它编码为无效            |
| timestamp | int    | 当前时间戳，单位秒                                     |
| message   | string | 返回消息                                               |
| sign      | string | 签名-对响应参数中的data字段内容的使用商户端RSA私钥签名 |

## 7. SDK实例

## 8. 全局状态码

> 🔔接口返回值状态码含义说明清单

| 状态码 | 含义                         | 备注 |
| :----- | :--------------------------- | :--- |
| 1      | 成功                         |      |
| 10701  | 创建用户失败：已经存在此用户 |      |

## 9. Token 类型

| TokenID | Value         | Description                      |
| :------ | :------------ | :------------------------------- |
| 1       | ETH-ETH       | ETH Network ETH                     |
| 2       | ETH-USDT      | ETH Network USDT                    |
| 3       | ETH-USDC      | ETH Network USDC                    |
| 4       | TRON-TRX      | TRON Network TRX                    |
| 5       | TRON-USDT     | TRON Network token：USDT            |
| 6       | BNB-BNB       | BNB Smart Chain Network BNB         |
| 7       | BNB-USDT      | BNB Smart Chain Network token：USDT |
| 8       | BNB-USDC      | BNB Smart Chain Network token：USDC |
| 9       | ARB-ETH       | Arbitrum One Network token：ETH     |
| 10       | ARB-USDT      | Arbitrum One Network token：USDT   |
| 11       | ARB-USDC      | Arbitrum One Network token：USDC   |
|12	       |ARB-DAI        | Arbitrum One Network token：DAI    |
|13	       |ETH-DAI        | ETH Network DAI                    |
|14        |TRON-USDD      |TRON  Network  USDD                 |
|15	       |BNB-ETH        |BNB  Network  ETH                   |
|16	       |BNB-DAI        |BNB  Network  DAI                    |
|17	       |Optimism-ETH   |Optimism  Network  ETH              |
|18        |Optimism-USDT  |Optimism  Network  USDT             |
|19	       |Optimism-USDC  |Optimism  Network  USDC             |
|20	       |Polygon-MATIC  |Polygon  Network  MATIC             |
|21	       |Polygon-USDT   |Polygon  Network  USDT              |
|22	       |Polygon-USDC   |Polygon  Network  USDC              |
|23	       |Base-ETH       |Base  Network  ETH                  |
|24	       |Base-USDC      |Base  Network  ETH                  |
|25	       |AVAX-AVAX      |AVAX  Network  AVAX                 |
|26	       |AVAX-USDT      |AVAX  Network  USDT                 |
|27	       |AVAX-USDC      |AVAX  Network  USDC                 |
|28	       |OpBNB-BNB      |OpBNB  Network  BNB                 |
|29	       |OpBNB-USDT     |OpBNB  Network  USDT                |
|30        |TRON-USDC      |TRON  Network  USDC                 |
|31        |BNB-BTCB       |BNB  Network  BTCB                  |

## 10. 公链ID

| 币名          | 全名称              | 区块链浏览器地址                | 
| :------------ | :------------------ | :------------------------------ |
| eth           | eth                 | https://etherscan.io            |
| trx           | Tron                | https://tronscan.io             |
| btc           | btc                 | https://blockchair.com/bitcoin  | 
| sol           | solana              | https://explorer.solana.com     | 
| xrp           | xrp                 | https://xrpscan.com             |
| eth_optimism  | optimism            | https://optimistic.etherscan.io |
| bnb           | bnb                 | https://bscscan.com             |
| matic_polygon | MATIC polygon chain | https://polygonscan.com         |
| TON           | Toncoin             | https://tonscan.org/            |
| ARB           | Arbitrum One        | https://arbiscan.io/            |
