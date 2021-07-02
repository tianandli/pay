package com.lijie.pay.controller;


import com.lijie.pay.utils.DateUtil;
import com.lijie.pay.wx.WechatPayUtil;
import com.lijie.pay.wx.wxpay.GetWxOrderno;
import com.lijie.pay.wx.wxpay.wxToPay;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author
 */
@RestController
@Slf4j
@RequestMapping("/api/wxpay")
public class ApiWxPayController {

    private static Logger logger = LoggerFactory.getLogger(ApiWxPayController.class);


    /**
     * 发起微信支付
     */
    @RequestMapping("/unifiedOrder")
    public String getCodeUrl() throws IOException {
        Integer orderid = 1;

        BigDecimal bigDecimal = new BigDecimal("0.01");

//         商品订单号
        String trade_no = "OX474918021413220356";

        //返回的这个字符串如：weixin://wxpay/bizpayurl?pr=BgdTmI8zz，通过点击这个就可以跳转到微信支付的界面
        String wxPayJsonStr = wxToPay.payMsg(localIp(), trade_no, bigDecimal.toString(), "测试JP商品");

        System.out.println("处理业务逻辑");
        return wxPayJsonStr;

//        GenerateQrCodeUtil.encodeQrcode(wxPayJsonStr, responses);
    }


    @RequestMapping("/notify")
    public String parseScanPayNotifyResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Date nowDate = DateUtil.getNowDate();
        log.info("收到微信回调了");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        log.info("微信返回的东西 ===>   {}", resultxml);
        Map<String, String> params = GetWxOrderno.doXMLParse(resultxml);
        log.info("解析成xml的东西 ===>  {}", resultxml);
        outSteam.close();
        inStream.close();
        String result_code = params.get("result_code");
        if ("SUCCESS".equalsIgnoreCase(result_code)) {
            if (!WechatPayUtil.isNotifySign(params)) {
                // 支付失败
                return "error";
            } else {
                log.info("===============付款成功==============");
                // ------------------------------
                String out_trade_no = params.get("out_trade_no");
                String trade_no = params.get("trade_no");
                String total_fee = params.get("total_fee");
                System.out.println("处理业务逻辑");


                return "success";
            }
        } else {
            return "error";
        }
    }


    /**
     * 获取本机Ip
     * <p>
     * 通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
     * 获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
     *
     * @return
     */
    private static String localIp() {
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            logger.warn("获取本机Ip失败:异常信息:" + e.getMessage());
        }
        return ip;
    }

    /**
     * 微信支付签名算法sign
     *
     * @param parameters
     * @return
     */
    public static String createSign(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + "2zsNuzcKQbZiOeS2E31gvMztoIyMtZA0");//这里是商户那里设置的key
        System.out.println("签名字符串:" + sb.toString());
//        System.out.println("签名MD5未变大写：" + MD5Util.MD5Encode(sb.toString(), characterEncoding));
        String sign = md5Password(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 生成32位md5码
     *
     * @param key
     * @return
     */
    public static String md5Password(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


}
