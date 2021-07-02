package com.lijie.pay.controller;//package net.cnool.gztf.event.pay.ali;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lijie.pay.ali.AlipayAPIClientFactory;
import com.lijie.pay.ali.AlipayConfig;
import com.lijie.pay.ali.MatrixToImageWriter;
import com.lijie.pay.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/api/alipay")
@Slf4j
public class AliPayController  {



    @RequestMapping(value = "/unifiedOrder")
    public String buildOrder(
            HttpServletRequest requests,
            HttpServletResponse responses
    ) throws AlipayApiException, IOException {
        Integer orderid = 1;
        BigDecimal bigDecimal = new BigDecimal("0.01");



        //获得初始化的AlipayClient
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类

        //设置回调地址
        request.setNotifyUrl(AlipayConfig.notify_url);
        //根据订单号查询订单信息
        Map<String, Object> maps = new HashMap<>();
        //商品订单号
        String trade_no = "OX474918021413220359";
        maps.put("out_trade_no", trade_no);
        // fixme 金额直接设置为 0.01
        maps.put("total_amount", bigDecimal.toString());
        maps.put("subject", "JP服务");
        maps.put("store_id", "NJ_001");
        maps.put("timeout_express", "90m");
        //把订单信息转换为json对象的字符串
        String postdata = JSONObject.toJSONString(maps);
        request.setBizContent(postdata);
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        String body = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        System.out.println("jsonObject = " + jsonObject);
        /**
         * 下面的qr_code大概就是"qr_code":"https://qr.alipay.com/bax05040hy6jvmm9lbet00cd"，拿到这个，给支付宝的下单就完成了，下面将这个url封装到一个二维码里，
         * 通过扫码就会转让到支付宝的付款界面了
         */
        String qr_code = jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");

//        responses.setContentType("UTF-8");
    //下面生成二维码
        ServletOutputStream sos = null;
        try {
            sos = responses.getOutputStream();
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            // 指定编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qr_code, BarcodeFormat.QR_CODE, 200, 200, hints);
            //生成二维码
            MatrixToImageWriter.writeToStream(bitMatrix, "png", sos);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //这里写业务逻辑
        return qr_code;
    }

    /**
     * 支付统一回调接口
     * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.afod0z&
     * treeId=193&articleId=105301&docType=1 异步通知地址
     * 收到支付成功异步通知要回复success，否则导致支付宝重发数次通知，重发通知服务器异步通知参数notify_id是不变的。
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "notify", produces = "text/html;charset=UTF-8")
    public String notify(HttpServletRequest request) throws Exception {
        Date nowDate = DateUtil.getNowDate();
        log.info("ali回调");
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info("[/alipay/notifyCallback]");
        String subject = params.get("subject");
        log.info("=========" + subject);
        String body = params.get("body");
        String gmt_create = params.get("gmt_create");
        String gmt_payment = params.get("gmt_payment");
        String gmt_refund = params.get("gmt_refund");
        String refund_fee = params.get("refund_fee");
        String gmt_close = params.get("gmt_close");
        String fund_bill_list = params.get("fund_bill_list");
        String trade_status = params.get("trade_status");
        String out_trade_no = params.get("out_trade_no");
        String trade_no = params.get("trade_no");
        String notify_time = params.get("notify_time");
        String notify_id = params.get("notify_id");
        // 商户业务ID，主要是退款通知中返回退款申请的流水号
        String out_biz_no = params.get("out_biz_no");
        String buyer_id = params.get("buyer_id");
        String seller_id = params.get("seller_id");
        String total_amount = params.get("total_amount");
        String receipt_amount = params.get("receipt_amount");
        String invoice_amount = params.get("invoice_amount");

        log.info(params.toString());
        log.info("支付宝回调地址！");
        log.info("订单标题：" + subject);
        log.info("商品描述：" + body);
        log.info("交易创建时间：" + gmt_create);
        log.info("交易付款时间：" + gmt_payment);
        log.info("交易退款时间：" + gmt_refund);
        log.info("交易结束时间：" + gmt_close);
        log.info("商户的订单编号：" + out_trade_no);
        log.info("支付宝交易号：" + trade_no);
        log.info("支付的状态：" + trade_status);
        log.info("通知时间：" + notify_time);
        log.info("通知校验ID：" + notify_id);
        log.info("商户业务号：" + out_biz_no);
        log.info("买家支付宝用户号：" + buyer_id);
        log.info("卖家支付宝用户号：" + seller_id);
        log.info("订单金额：" + total_amount);
        log.info("实收金额：" + receipt_amount);
        log.info("开票金额：" + invoice_amount);
        log.info("支付金额信息：" + fund_bill_list);
        log.info("总退款金额：" + refund_fee);

        // 切记
        // AlipayConfig.ALIPAY_PUBLIC_KEY是支付宝的公钥，不是应用公钥，请去open.alipay.com对应应用下查看。
        boolean verify_result = AlipaySignature.rsaCheckV1(params,
                AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
                AlipayConfig.SIGNTYPE);
        // 验证失败可能原因：AlipayConfig.ALIPAY_PUBLIC_KEY是支付宝的公钥，不是应用公钥，请去open.alipay.com对应应用下查看
        // 创建订单时请求字段有乱码,异步回调才去处理中文乱码，导致验证失败(解决创建订单处理中文乱码,异步回调不处理)
        if (verify_result) {// 验证成功
            // 请在这里加上商户的业务逻辑程序代码
            log.info("-----验证成功----");

            // 注意：
            // 状态TRADE_SUCCESS的通知触发条件是商户签约的产品支持退款功能的前提下，买家付款成功；
            // 交易状态TRADE_FINISHED的通知触发条件是商户签约的产品不支持退款功能的前提下，买家付款成功；或者，商户签约的产品支持退款功能的前提下，交易已经成功并且已经超过可退款期限。
            if (trade_status.equals("TRADE_FINISHED")) {
                // 业务处理

                log.info("-----产品不支持退款功能的前提下----");
                log.info("-----付款成功----");
            } else if (trade_status.equals("TRADE_SUCCESS")) {
//                log.info("-----产品支持退款功能的前提下----");
                log.info("-----付款成功----");
                //这里写业务逻辑

            } else if (trade_status.equals("TRADE_CLOSED")) {// 当商户使用站内退款时，系统会发送包含refund_status和gmt_refund字段的通知给商户。

            }

            return "success";
        } else {
            log.info("-----验证失败----");
            return "failure";
        }

    }

    @ResponseBody
    @RequestMapping("refund")
    public String refund(String out_trade_no, String trade_no,
                         String refund_amount, String refund_reason, String out_request_no)
            throws Exception {
        // 获得API调用客户端
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();

        AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();

        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(out_trade_no);
        model.setTradeNo(trade_no);
        model.setRefundAmount(refund_amount);
        model.setRefundReason(refund_reason);
        model.setOutRequestNo(out_request_no);
        alipay_request.setBizModel(model);

        AlipayTradeRefundResponse alipay_response = alipayClient
                .execute(alipay_request);
        log.info(alipay_response.getBody());
        if (alipay_response.isSuccess()) {
            return "1";
        } else {
            return "0";
        }

    }

}