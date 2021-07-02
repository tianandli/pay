package com.lijie.pay.wechat.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: JP
 * @description: 微信配制类
 * @author: btxu
 * @date: 2019-07-08 11:12
 **/

@ConfigurationProperties("wechat")
@Configuration
@Data
public class WeChatProperties {
    /**
     * 是否获取token
     */
    private boolean getToken;

    private String appId;
    private String appSecret;
    /**
     * 通信是双方约定
     */
    private String token;
    /**
     * 消息模版
     */
    private String templateMessageId = "Ef-AG5hNAZMD4kHrb_NRu0eStHQiqA5bvEum2lswCMU";
}
