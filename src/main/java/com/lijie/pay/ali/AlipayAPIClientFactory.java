package com.lijie.pay.ali;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayAPIClientFactory {

    /**
     * API调用客户端
     */
    private static AlipayClient alipayClient;

    /**
     * 获得API调用客户端
     *
     * @return
     */
    public static AlipayClient getAlipayClient() {

        if (null == alipayClient) {
            // 实例化客户端
            alipayClient = new DefaultAlipayClient(AlipayConfig.URL,
                    AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
                    AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        }
        return alipayClient;
    }
}
