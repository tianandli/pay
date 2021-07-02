package com.lijie.pay.wechat.listener;


import com.lijie.pay.constant.WechatConstant;
import com.lijie.pay.wechat.property.WeChatProperties;
import com.lijie.pay.wechat.token.TokenThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: JP
 * @description: 微信获取Token监听器
 * @author: btxu
 * @date: 2019-07-08 11:10
 **/

@Slf4j
@Component
public class WeChatTokenListener implements InitializingBean {

    @Autowired
    WeChatProperties weChatProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        WechatConstant.APPID = weChatProperties.getAppId();
        WechatConstant.APPSECRET = weChatProperties.getAppSecret();
        WechatConstant.TOKEN = weChatProperties.getToken();
        log.info("设置微信参数成功");
        if (weChatProperties.isGetToken()) {
            new Thread(new TokenThread()).start();
            log.info("启动获取微信AccessToken");
        }
    }
}

