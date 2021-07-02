package com.lijie.pay.ali;

public class AlipayConfig {
    // 沙箱应用appid
    public static String APPID = "2016101400687905";
    // 私钥 pkcs8格式的 TODO 修改
    public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCgI7Kv28+A17X9U3XZHKmz1MGuJ6SbHKb1j1hgaX4zLMXLfaKuzrSj1LmUk4VrLrMAU7a3qHXnnOfjZbLSfi059sjQIvkYQBGBrTq0u0fnJZmus+HmQqMVk63MQxeKrzc4XkZrQxee42R47fPufrL/6dj8HvOa59VZGfDUigYtcVzmCtPiVw4kSMlnmC9FEIQgKwYc9woh1QK+qpK1E+Ktj21JOho7xMP7L5S4UkHtWSb4eizCmiqFa9DHXPlrP1ODdFJWzem8Ggx/Dd2N9GLtNB4VGJeKA9O8fW76aP6VR05iygm/7YM5+KoeuFGumG37PHfBMziz8+1ApAOoRlqXAgMBAAECggEAFG4ZeNgZM/3RKP2B1m4StG+bOgWyuVtBbaiZBMvQnuTCY0CnBZg3cYNNHchoSBFQIuWLNQkX6bGZ3+f0lWglivSSEAO2NcLOuPWJvM770GB6p9ApLMApoDIUBTA2C6po6BcW8s4/cDTgd5EcArxFR+LknN2yBHw5OsHVeLgXK6rLrlEynir+r1kQbmKxJCmBUC7+d26JqcqN0uyBrsLn/sppW8d9XgiseeLvtJZdDwBl7BLNHyOGa4w5QzwelrIX4AoZaC+X/aLS3qX/JBnTGsr5dtOYpez3RK9mK9L/hB8buHkmxR2Zog83L24kAGey41CkQF8B34PZ08fdpLKTwQKBgQDmosLsqyVMW6eGym7dZCNzDmYarE7vUYhNhz2vs9z3tXzeiqNB4v9qWxhbA3Lx/iFE1nsMWEmlrbvLFIqIf2ITaP2WmbTaWsHMTBcgx2LQgx5rtq4h84VydzMcRSbCPUucnGnLjh0vlAVlsEi9NcAgc/5MgqtuyrZCD0Cyn52ioQKBgQCxwDUbArnzrl33xNbJiiJETa4O+ZTvS4BPw2b9TDYhFLgDuznSc7a6wp2XWh4PYToe7Shk6Q3/rl0dE6/RMOsaYKRPdqUiNBizxNVuW+0NomO0VaBk8jUatMBUoVItbcuBiIAMR0vo+/4RbpwOIfWQpK51rVSOhrBaxRHUAHoqNwKBgQC4Ky52aLAvCl17YDkpZuZ74PqdwAyERFoQdrdGj90anP0qhBCb81U8K6QGQXnPTbxpSNAXaoTc3R/BqhR5rSa3Eza0xObLGn/40KYDUdRRZvKq1sS0bc6ZOiBBKeMqZmHznF9K4Z/it1OcASoElWBnSdi+ZfXZUdw6Yg2q1ZS+IQKBgQCWYm7Lrm/S2ghIcvHhy4uYTDxiEQjPuAoT8dc1oH//Vv8ksQeHpTzJH5SjE6xxCKpatf1B+udMFaJT0m8ixodyZqc9T70AhiEMHxEP8JhLu8MtNHCiV0VUle5O67mSE91kdy8WtE9mC6T17KX9Uv/m411y1DXA6/VSpn6h5dF12wKBgQC/6tsSlrQbSV1SynIP36UA/pxUci5kcDpm8qdsVQfgtlF047I/ZvrAB2foTx9RhKGfYQwCUaVRp1iAwys/zXHin69vCJjIYjeh0YqPa0JOxDng4F3whS2OvTdg47HGQbRwvEEdATMH8uVJjnRWkwd1jDFY9jeG7HWscfRWdK+ATg==";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://d23613c225b6.ngrok.io/api/alipay/notify";
//    public static String notify_url = "https://uk-api.jpvat.com/api/alipay/notify";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 商户可以自定义同步跳转地址
    public static String return_url = "";
    // 支付宝网关
//    public static String URL = "https://openapi.alipay.com/gateway.do";//正式环境
    public static String URL = "https://openapi.alipaydev.com/gateway.do";//沙箱环境
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuHf50fuW118pUHxt+GQ6jiM2EdHYyz6Ej8CAeyzTOxkzh/uq8emVhhQ7M1xCPAHrgawY/at2bXR04msSGW7MwPeXmpeX3CKXc24cnWL9m9287bmobxjP1xHhIjC5uvQtbeInwH7mkFd0JIOUbEkzZ8SwMX/MiJ8ZlcwGcZqgj+8mQuOyrY7GfFK6fC8oTIH5LQQncXOZUWpznK43xJpRPXuzD2JE0Jkwnwz990Frt/IuyK18+KAmnDdKaic1OCqQjjVl8pnbQ2kIVOOVSGMkj3CxjGDClBjHQRt3iyKALRVdYFaMNxHiTQOh3Uoaiqtvk06JRIwC46qsfnphkwP1jwIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
