/**
 * @ClassName: XmlUtil
 * @Description: TODO
 * @author: cc
 * @date: 2018年8月18日 下午12:06:10
 */
package com.lijie.pay.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: cc.feefox.wechat.common.util
 * @author: cc
 * @date: 2018年8月18日 下午12:06:10
 */
public class XmlUtil {

    /**
     * 解析微信发来的请求（XML） xml示例 <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
     * <FromUserName><![CDATA[FromUser]]></FromUserName>
     * <CreateTime>123456789</CreateTime> <MsgType><![CDATA[event]]></MsgType>
     * <Event><![CDATA[CLICK]]></Event> <EventKey><![CDATA[EVENTKEY]]></EventKey>
     * </xml>
     *
     * XmlUtil.java
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseStreamToMap(InputStream inputStream) throws Exception {

        Map<String, String> map = new HashMap<>();
        try {
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            inputStream.close();
            inputStream = null;
        }

        return map;
    }

    /**
     * XStream工具类
     *
     * @Package: cc.feefox.wechat.common.util
     * @author: cc
     * @date: 2018年8月20日 下午7:49:50
     */
    static class XStreamFactroy {

        private static final String START_CADA = "<![CDATA[";
        private static final String END_CADA = "]]>";

        /**
         * 是否启用<![CDATA[]]>
         *
         * @param flag true 表示启用 false表示不启用
         * @return
         */
        static XStream init(boolean flag) {
            XStream xstream = null;
            if (flag) {
                xstream = new XStream(new XppDriver() {
                    public HierarchicalStreamWriter createWriter(Writer out) {
                        return new PrettyPrintWriter(out) {
                            @Override
                            protected void writeText(QuickWriter writer, String text) {
                                if (!text.startsWith(START_CADA)) {
                                    text = START_CADA + text + END_CADA;
                                }
                                writer.write(text);
                            }
                        };
                    }
                });
            } else {
                xstream = new XStream();
            }
            return xstream;
        }
    }
}
