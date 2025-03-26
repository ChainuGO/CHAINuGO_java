# ChainuGO SDK

![ChainuGO](./doc/images/ChainuGO.png)

English | [简体中文](readme-cn.md)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 1. Welcome to use chain pay sdk

### 1.1. Prepare

1. Become a Partner

The unique identifier verifies the partner's identity. Different partners configure different parameters. New partners can contact the platform.

Production environment management platform:

[https://admin.chainugo.com/](https://admin.chainugo.com/)

Sandbox environment management platform:

[https://testnet.chainugo.com/](https://testnet.chainugo.com/)

Get API key:

![system-apikey](./doc/images/system-apikey-en.png)

Platform RSA Public Key:

![system-apikey-plat-pub](./doc/images/system-apikey-plat-pub-en.jpg)

Platform withdrawal risk control RSA public key:

![system-apikey-risk-pub](./doc/images/system-apikey-risk-pub-en.jpg)

Callback website need to enable IP whitelist:

![system-whitelist](./doc/images/system-whitelist-en.png)

View transaction list:

![transaction-list-en](./doc/images/transaction-list-en.png)

Set callback website:

![system-callback-en](./doc/images/system-callback-en.png)

1. Provide a list of main chains and contract currencies that need to be supported

Contract currency requires the contract address, main chain, accuracy, name and other basic information to be provided to the business counterpart.

3. Interface sandbox environment debugging

Interface sandbox environment docking, joint debugging, and drills. The interface uses `RSA2048` bit two-way signature verification to ensure communication credibility. Merchants need to give the `public key` to the platform in advance for the platform to verify the merchant's request parameters. Merchants need to obtain the `platform public key` in advance for the platform's response result verification.

### 1.2. Signature Rules

* Use the `md5` algorithm to generate the sign signature from the merchant's secret code, timestamp (unit: milliseconds) and the transmitted data;
* Use the `RSA` algorithm to generate the clientSign signature from the transmitted data;

#### 1.2.1. sign Rules

1. Register as a partner and obtain Key and Secret
2. Parse the JSON Body of the request, sort the keys in JSON from small to large according to ASCII, and concatenate the string dataStr=key1=value1&key2=value2&key3=value3&...
3. Generate timestamp (unit: milliseconds)
4. Encrypt and generate sign:

Plain text before encryption: strToHash = SecretKey+dataStr+timestamp

Perform MD5 encryption on the plain text strToHash above to generate sign
5. Put key, timestamp, and sign in the http head

#### 1.2.2. clientSign Rules

Create a private key

```bash
openssl genrsa -out rsa_private_key.pem 2048
```

Create a public key from a private key

```bash
openssl rsa -in rsa_private_key.pem -out rsa_public_key.pem -pubout
```

Detailed explanation of the signature algorithm

1. Get the request parameters and format them to obtain a new parameter formatted string:
2. Sign the first step data with the RSA private key and save the signature result to a variable:

Generate a signature string for the following parameter array: `user_id = 1 coin = eth address = 0x038B8E7406dED2Be112B6c7E4681Df5316957cad amount = 10.001 trade_id = 20220131012030274786`

Sort each key in the array from a to z. If the same first letter is encountered, look at the second letter, and so on. After the sorting is completed, connect all the array values ​​with the "&" character, such as $dataString:

`address=0x038B8E7406dED2Be112B6c7E4681Df5316957cad&amount=10.001&coin=eth&trade_id=20220131012030274786&user_id=1`

This string is the concatenated string.

Use the private key to sign the data with `RSA-md5`.

### 1.3. Public Information

The following information is common to all interfaces and will not be repeated for each interface.

#### 1.3.1. Production Environment

API address: 

[https://api.chainugo.com/sdk/](https://api.chainugo.com/sdk/)

#### 1.3.2. Sandbox environment

API address: 

[https://testnet.chainugo.com/sdk/](https://testnet.chainugo.com/sdk/)

#### 1.3.3. Request public parameters

Request header definition

| Parameter Name | Constraint | Example                          | Description                                |
| :------------- | :--------- | :------------------------------- | :----------------------------------------- |
| key            | length:64  | ithujj3onrzbgw5t                 | Partner key                                |
| timestamp      | length:32  | 1722586649000                    | Timestamp of the request (in milliseconds) |
| sign           | length:32  | 9e0ccfe3915e94bcc5bf7dd51ad4e8d9 | Partner secret signature                   |
| clientSign     | length:512 | 4bcc5bf7dd51ad4e8d9  ...         | Partner RSA signature                      |

#### 1.3.4. Public Information Notice

| Name               | Type      | Example                            | Description                                                 |
| :----------------- | :-------- | :--------------------------------- | :---------------------------------------------------------- |
| Global status code | integer   | 1                                  | 1 means success, see Global Status Code for details         |
| Information        | string    | ok                                 | Returns information in text format                          |
| Data               | json      | {"OpenID":"HEX..."}                | Returns specific data content                               |
| Time               | timeStamp | 1722587274000                      | UTC time is unified time without time zone, in milliseconds |
| Signature          | sign      | 9e0ccfe3915e94bcc5bfbBsC5EUxV6 ... | The platform uses RSA to sign all data                      |

## 2. Install

```bash
```

## 3. Business flow

### 3.1. Recharge flow

![pay-service-flow](./doc/images/pay-service-flow.png)

### 3.2. withdraw flow

![pay-service-withdraw-flow](./doc/images/pay-service-withdraw-flow.png)

## 4. User Management

### 4.1. Create user

* Function: Create a new platform user, which requires passing the user's unique ID

#### HTTP Request

> POST ： `/user/create`

#### Request Parameters

| Paramter | Required | Type   | Description                                                                                                                       |
| :------- | :------- | :----- | :-------------------------------------------------------------------------------------------------------------------------------- |
| OpenId   | Y        | string | It is recommended to use a platform-wide prefix (such as HASH for partners) + user-unique number to form the user's unique OpenId |

#### Return parameter description

| Paramter    | Type   | Description                      |
| :---------- | :----- | :------------------------------- |
| code        | int    | Global status code               |
| msg         | string | Status description               |
| data.OpenId | string | Returns the user's unique OpenId |
| sign        | string | Platform signature               |

### 4.2. Create wallet

* Function: Create a wallet account for the user corresponding to this blockchain network
* Precondition: The user with the specified OpenId has been created successfully

#### HTTP Request

> POST ： `/wallet/create`

#### Request Parameters

| Paramter | Required | Type   | Description          |
| :------- | :------- | :----- | :------------------- |
| ChainID  | Y        | string | Public chain ID      |
| OpenId   | Y        | string | User's unique OpenId |

#### Return parameter description

| Paramter     | Type   | Description          |
| :----------- | :----- | :------------------- |
| code         | int    | Global status code   |
| msg          | string | Status description   |
| data.address | string | Wallet address       |
| data.UserId  | string | User ID              |
| data.ChainID | string | Public chain ID      |
| data.OpenId  | string | User's unique OpenId |
| sign         | string | Platform signature   |

## 5. Withdrawl

### 5.1. Partner User Withdrawal

* Function: Provide an operation interface for partner users to withdraw funds. It is necessary to withdraw funds from the partner's corresponding Token withdrawal fund pool account to the withdrawal wallet address set by the user. For partners, a safe callback address can be set to verify the legality of the withdrawal. If the verification is legal, directly operate the merchant fund pool wallet to complete the withdrawal.
* The withdrawal transaction interface will check whether the default withdrawal hot wallet has sufficient wallet fees and withdrawal assets;
* The withdrawal interface will use the security verification code as the unique parameter requirement for withdrawal transactions by default. It is recommended to use the unique withdrawal order number of the business platform as the security code. Repeated security verification code submission will return an error code;
* All withdrawal transaction requests will match the risk control review rules configured on the channel platform. If the parameter request is legal, the transaction request will be received. Transactions that meet the automatic review rules will be submitted to the network immediately, and the Hash information of the submitted transaction will be returned (return field data); for withdrawal transaction requests that need to be reviewed twice in the channel, (code=2) will be returned. There is no need to submit the withdrawal request again. The administrator needs to complete the second review on the channel platform. After the second review is completed, the transaction order will call back to notify the status change of the withdrawal transaction request.
* Precondition: The fund pool of the corresponding currency has an amount exceeding the withdrawal limit (pay special attention to ETH network Token withdrawal, which requires the fund pool wallet to have a certain ETH handling fee balance)
* ⚠️ Note: **For the withdrawal operation of initiating a blockchain, it is necessary to ensure that the pre-review process is completed before calling the interface. Once a blockchain transaction is initiated, it cannot be revoked or returned. **

#### HTTP Request

> POST ： `/partner/UserWithdrawByOpenID`

#### Request Parameters

| Paramter      | Required | Type   | Description                                                                                                |
| :------------ | :------- | :----- | :--------------------------------------------------------------------------------------------------------- |
| OpenId        | Y        | string | User's unique OpenId                                                                                       |
| TokenId       | Y        | string | TokenId                                                                                                    |
| Amount        | Y        | float  | The amount of currency the user withdraws, accurate to 2 decimal places                                    |
| AddressTo     | Y        | string | Destination wallet for withdrawal                                                                          |
| CallBackUrl   | N        | string | Callback to notify the user of the withdrawal progress, optional, using the partner's default callback url |
| SafeCheckCode | N        | string | Security verification code for user withdrawal transactions                                                |

#### Return parameter description

| Paramter | Type   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| :------- | :----- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| code     | int    | Status code</br>0 Parameter error, or duplicate order number, or incorrect withdrawal address format, or insufficient handling fee in the withdrawal wallet. msg can be used to view detailed information;</br>1 The withdrawal transaction is successfully submitted and has been submitted to the blockchain network. The data contains the unique hash of the submitted transaction;</br>2 The withdrawal transaction is successfully submitted. The transaction can only be completed after the channel has reviewed it twice. After the review is completed, the transaction information will be updated through callback;</br>-1 The withdrawal transaction failed. You can resubmit the withdrawal request; |
| msg      | string | Status description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| data     | string | Transaction hash                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| sign     | string | Platform signature                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |

### 5.2. Query user deposit and withdrawal records

* Function: Provide partners with a data interface to query user recharge/withdrawal records, supporting paging query data
* Prerequisites: None

#### HTTP Request

> POST ： `/wallet/GetPayChargeRecords`

#### Request Parameters

| Paramter | Required | Type   | Description                                                                                                                                           |
| :------- | :------- | :----- | :---------------------------------------------------------------------------------------------------------------------------------------------------- |
| OpenId   | Y        | string | OpenId                                                                                                                                                |
| TopStart | Y        | int    | The starting serial number of the current record, the number of client-defined pages and the calculation of the starting and ending serial numbers    |
| TopEnd   | Y        | int    | The ending serial number of the current record                                                                                                        |
| PayType  | Y        | int    | Filter type, 0 means no filtering, 1 means only querying withdrawal records; 2 means only querying recharge records                                   |
| hashCode | N        | string | Can be empty, specify Hash to query transaction records                                                                                               |
| safeCode | N        | string | Can be empty, specify safeCode, generally withdrawals can query the withdrawal record status through the unique withdrawal security verification code |

#### Return parameter description

| Paramter                                | Type     | Description                                                                                                                                                                                                                                                                                                                      |
| :-------------------------------------- | :------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| code                                    | int      | status                                                                                                                                                                                                                                                                                                                           |
| msg                                     | string   | status description                                                                                                                                                                                                                                                                                                               |
| data.userPayChargeRecords[]             | object[] | Deposit and withdrawal transaction data collection                                                                                                                                                                                                                                                                               |
| data.userPayChargeRecords.OpenID        | string   | OpenId                                                                                                                                                                                                                                                                                                                           |
| data.userPayChargeRecords.PayOrCharge   | int      | 1 recharge, 2  withdrawal                                                                                                                                                                                                                                                                                                        |
| data.userPayChargeRecords.FromAddress   | string   | from address                                                                                                                                                                                                                                                                                                                     |
| data.userPayChargeRecords.ToAddress     | string   | to address                                                                                                                                                                                                                                                                                                                       |
| data.userPayChargeRecords.HashCode      | string   | Blockchain transactions Hash                                                                                                                                                                                                                                                                                                     |
| data.userPayChargeRecords.SafeCode      | string   | safeCode                                                                                                                                                                                                                                                                                                                         |
| data.userPayChargeRecords.Amount        | float    | Amount: accurate to 2 decimal places                                                                                                                                                                                                                                                                                             |
| data.userPayChargeRecords.Status        | int      | Transaction status,</br> 1 successfully completed,</br>  2 waiting for review,</br>  3 waiting for block transaction confirmation</br>  -1 means transaction failed (business platform needs to roll back user assets)</br>  -2 means review rejected or transaction canceled (business platform needs to roll back user assets) |
| data.userPayChargeRecords.NoticeTimes   | int      | Block confirmation times                                                                                                                                                                                                                                                                                                         |
| data.userPayChargeRecords.NoticeUrl     | string   | Callback address                                                                                                                                                                                                                                                                                                                 |
| data.userPayChargeRecords.NoticeRespone | string   | Callback feedback                                                                                                                                                                                                                                                                                                                |
| data.userPayChargeRecords.CreateTime    | string   | Creation time                                                                                                                                                                                                                                                                                                                    |
| data.TotalCount                         | int      | Total number of transactions, providing paging query data                                                                                                                                                                                                                                                                        |
| sign                                    | string   | Platform Signature                                                                                                                                                                                                                                                                                                               |

### 5.3. Second review of withdrawal order

* Function: Merchant withdrawal order risk control secondary review interface.
* ⚠️ Note: **The platform assigns a separate risk control public key to merchants (different from the deposit/withdrawal public key)**

#### HTTP Request

> POST ： `/withdrawal/order/check`

#### Request Parameters


| Paramter         | Required | Type          | Description                                                                  |
| :--------------- | :------- | :------------ | :--------------------------------------------------------------------------- |
| data             | N        | object        | As follows                                                                   |
| data.order_id    | Y        | string        | Merchant-side transaction unique ID (trade_id when withdrawing)              |
| data.user_id     | Y        | string        | User to whom the order belongs                                               |
| data.coin_symbol | Y        | string        | Currency name in lowercase, subject to the currency provided by the platform |
| data.address     | Y        | string        | Withdrawal address                                                           |
| data.amount      | Y        | decimal(20,8) | Withdrawal amount                                                            |
| data.timestamp   | Y        | int           | Current timestamp                                                            |

#### Return parameter description

| Paramter         | Type   | Description        |
| :--------------- | :----- | :----------------- |
| status           | int    | 200 means success  |
| msg              | string | Status description |
| data             | object | Data object        |
| data.status_code | int    | 200 means success  |
| data.timestamp   | int    | Timestamp          |
| data.order_id    | int    | Transaction number |
| sign             | string | Platform signature |

## 6. Notification callback

Deposit/Withdrawal Transaction Callback Interface Description

1. Deposit and withdrawal transactions will repeatedly notify callbacks, and the transaction information and status of the last callback notification will prevail;
   
2. The business end is required to return valid callback information. The format refers to the return parameter description. The business end needs to return code=0 to indicate that the callback message has been processed and no further notification is required. Otherwise, the callback will continue to notify (initial 50 times every 2 seconds, and then every 10 minutes) until the message confirmation with code=0 is returned;

### 6.1. Token transfer callback notification

> POST 

* Function: Define the format of the callback message that the platform sends to the application for receiving Token transfer notifications, which is suitable for the application to handle event notification messages for Token transaction status (withdrawal or recharge). The application can design optional callback notification interface functions based on the application functions.

#### Request Parameters

| Paramter     | Required | Type   | Description                                                                                                                                                                                                                              |
| :----------- | :------- | :----- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| status       | Y        | int    | Transaction status: </br>1 Transaction (withdrawal or review) successful;</br> -1 Transaction failed on the chain</br> -2 Indicates withdrawal review rejected</br> 2 Waiting for review;</br> 3 Indicates being processed on the chain; |
| type         | Y        | int    | 1 indicates a deposit transaction; 2 indicates a withdrawal transaction                                                                                                                                                                  |
| hash         | Y        | string | Hash                                                                                                                                                                                                                                     |
| confirm      | Y        | int    | The number of confirmations of the transaction completed on the chain                                                                                                                                                                    |
| createdtime  | Y        | string | create time                                                                                                                                                                                                                              |
| from         | Y        | string | Transaction from address                                                                                                                                                                                                                 |
| to           | Y        | string | Transaction to address                                                                                                                                                                                                                   |
| amount       | Y        | string | Number of transactions                                                                                                                                                                                                                   |
| chainid      | Y        | string | chain Id id                                                                                                                                                                                                                              |
| tokenid      | Y        | string | tokenid                                                                                                                                                                                                                                  |
| tokenaddress | Y        | string | token contract address                                                                                                                                                                                                                   |
| safecode     | Y        | string | Valid for withdrawal orders, usually the unique number of the withdrawal order orderid                                                                                                                                                   |
| timestamp    | Y        | string | Transaction timestamp                                                                                                                                                                                                                    |
| tag          | N        | string | optional just for XRP，EOS                                                                                                                                                                                                               |
| sign         | Y        | string | Platform signature data **The recipient can use the platform public key to verify the reliability of the data returned by the platform. It is strongly recommended that the recipient verify the validity of the signature**             |

## 7. SDK

### 7.1. Configuration required

1. Register a business name to obtain `ApiKey` and `ApiSecret`;
2. Generate your own `RSA` password pair;
3. Prepare the platform's `RSA` public key;

## 8. Global status code

> 🔔Interface return value status code meaning list

| Status code | Meaning                                         | Remark |
| :---------- | :---------------------------------------------- | :----- |
| 1           | success                                         |        |
| 10701       | Failed to create user: This user already exists |        |

## 9. Token

| TokenID | Value         | Description                         |
| :------ | :------------ | :---------------------------------- |
| 1       | ETH-ETH       | ETH Network ETH                     |
| 2       | ETH-USDT      | ETH Network USDT                    |
| 3       | TRON-TRX      | TRON Network TRX                    |
| 4       | TRON-USDT     | TRON Network token：USDT            |
| 5       | BNB-BNB       | BNB Smart Chain Network BNB         |
| 6       | BNB-USDT      | BNB Smart Chain Network token：USDT |
| 11      | Polygon-MATIC | Polygon Network Matic               |
| 12      | Polygon-USDT  | Polygon Network token：USDT         |
| 13      | Polygon-USDC  | Polygon Network token：USDC         |
| 22      | BNB-USDC      | BNB Smart Chain Network token：USDC |
| 23      | BNB-DAI       | BNB Smart Chain Network token：DAI  |
| 24      | ETH-USDC      | ETH Network USDC                    |
| 25      | ETH-DAI       | ETH Network DAI                     |
| 130     | Optimism-ETH  | Optimism Network ETH                |
| 131     | Optimism-WLD  | Optimism Network token：WLD         |
| 132     | Optimism-USDT | Optimism Network token：USDT        |
| 100     | BTC-BTC       | BTC Network BTC Main chain currency |
| 200     | TON-TON       | TON Network TON Main chain currency |

## 10. chain ID

| Token         | Full name           | Blockchain browser address      | Chain ID unique identifier |
| :------------ | :------------------ | :------------------------------ | :------------------------- |
| eth           | eth                 | https://etherscan.io            | 1                          |
| trx           | Tron                | https://tronscan.io             | 2                          |
| btc           | btc                 | https://blockchair.com/bitcoin  | 3                          |
| sol           | solana              | https://explorer.solana.com     | 4                          |
| xrp           | xrp                 | https://xrpscan.com             | 5                          |
| eth_optimism  | optimism            | https://optimistic.etherscan.io | 10                         |
| bnb           | bnb                 | https://bscscan.com             | 56                         |
| matic_polygon | MATIC polygon chain | https://polygonscan.com         | 137                        |
| TON           | Toncoin             | https://tonscan.org/            | 15186                      |
