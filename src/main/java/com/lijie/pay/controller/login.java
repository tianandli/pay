package com.lijie.pay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 功能描述：
 *
 * @author: lijie
 * @date: 2021/6/25 15:30
 * @version: V1.0
 */
@RestController
@RequestMapping("/api/busaccount")
@Slf4j
public class login {
    @RequestMapping(value = "/loginWx")
    public HashMap<String, Object> loginWx(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long l = System.currentTimeMillis();
        String TIMESTAMP = String.valueOf(l);
        String notify = "https://uk-api.jpvat.com" + "/api/busaccount/notify_login?timestamp=TIMESTAMP";
        String timestamp = notify.replaceAll("TIMESTAMP", TIMESTAMP);
        String urlEncode = URLEncoder.encode(timestamp, "utf-8");

        String uQR = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx434cdf890d37c36f&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect";
        String realUrl = uQR.replaceAll("REDIRECT_URI", urlEncode);

        HashMap<String, Object> map = new HashMap<>();
        map.put("qrImg", realUrl);
        map.put("TIMESTAMP", TIMESTAMP);
//        TbWechatLogin tbWechatLogin = new TbWechatLogin();
//        tbWechatLogin.setTimestamp(TIMESTAMP);
//        tbWechatLogin.setIsdelete(false);
//        tbWechatLogin.setQrcode(realUrl);
//        wechatLoginService.save(tbWechatLogin);
        return map;
    }
}
