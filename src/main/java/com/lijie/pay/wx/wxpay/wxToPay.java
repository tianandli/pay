package com.lijie.pay.wx.wxpay;


import com.lijie.pay.wx.DefaultData;
import com.lijie.pay.wx.ShareFunction;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class wxToPay {
    //获取微信支付签名

    /**
     * @param ipaddress    ip地址
     * @param out_trade_no 商户订单号
     * @param payMoney     订单金额
     * @return
     */
    public static String payMsg(String ipaddress, String out_trade_no, String payMoney, String body) {
        try {
            if (ipaddress == null || "".equals(ipaddress) || "null".equals(ipaddress)) {
                return "";
            }
            if (out_trade_no == null || "".equals(out_trade_no) || "null".equals(out_trade_no)) {
                return "";
            }
            if (payMoney == null || "".equals(payMoney) || "null".equals(payMoney)) {
                return "";
            } else {
                BigDecimal bdPayMoney = new BigDecimal(payMoney);
                BigDecimal bdmin = new BigDecimal("0.01");
                if (bdmin.compareTo(bdPayMoney) > 0) {
                    return "";
                } else {
                    //BigDecimal d = new BigDecimal("2.225").setScale(2, BigDecimal.ROUND_HALF_UP);四舍五入（若舍弃部分>=.5，就进位）.下面的也就是0.01*100=1，再不保留小数位四舍五入
                    payMoney = bdPayMoney.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString();
                }
            }
        } catch (Exception e) {
            return "";
        }
        /**
         * 参考资料：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
         */
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("timeStamp", "");
        map.put("nonceStr", "");
        map.put("package", "");
        map.put("signType", "");
        map.put("paySign", "");
        String appid = DefaultData.wx_appid;
        String notify_url = DefaultData.wx_notify_url;
        String wx_mch_id = DefaultData.wx_mch_id;
        String trade_type = "NATIVE";//Native支付是商户系统按微信支付协议生成支付二维码，用户再用微信“扫一扫”完成支付的模式。该模式适用于PC网站支付、实体店单品或订单支付、媒体广告支付等场景
        String nonce_str = ShareFunction.getRandomString(25);
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", wx_mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);//商品简单描述
        //packageParams.put("attach", attach);//附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
        packageParams.put("out_trade_no", out_trade_no);//商户订单号
        packageParams.put("total_fee", payMoney);//订单总金额，单位为分
        packageParams.put("spbill_create_ip", ipaddress);//APP和网页支付提交用户端ip
        packageParams.put("notify_url", notify_url);//回调地址
        packageParams.put("trade_type", trade_type);//交易类型
//		packageParams.put("openid", openid);//用户openid
        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(appid, DefaultData.wx_key);
        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>"
                + "<appid>" + appid + "</appid>"
                + "<mch_id>" + wx_mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<sign><![CDATA[" + sign + "]]></sign>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<total_fee>" + payMoney + "</total_fee>"
                + "<spbill_create_ip>" + ipaddress + "</spbill_create_ip>"
                + "<notify_url>" + notify_url + "</notify_url>"
                + "<trade_type>" + trade_type + "</trade_type>"
                + "</xml>";
        try {
            reqHandler.genPackage(packageParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//微信提供的统一下单url
        String prepay_id = "";
        try {
            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
            if ("".equals(prepay_id)) {
                log.info("统一支付接口获取预支付订单出错");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return prepay_id;
    }


}
