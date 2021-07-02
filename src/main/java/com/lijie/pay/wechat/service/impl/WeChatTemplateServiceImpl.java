
package com.lijie.pay.wechat.service.impl;


import com.lijie.pay.constant.SystemConstant;
import com.lijie.pay.constant.VatConstants;
import com.lijie.pay.constant.WechatConstant;
import com.lijie.pay.utils.HttpUtil;
import com.lijie.pay.utils.JsonUtil;
import com.lijie.pay.wechat.TemplateMsgResult;
import com.lijie.pay.wechat.WeChatTemplateMsg;
import com.lijie.pay.wechat.property.WeChatProperties;
import com.lijie.pay.wechat.service.WeChatTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Package: cc.feefox.wechat.template.service.impl
 * @author: cc
 * @date: 2018年8月20日 下午2:55:19
 */
@Service
@Slf4j
public class WeChatTemplateServiceImpl implements WeChatTemplateService {

    private static final String OK = "ok";
    @Autowired
    WeChatProperties weChatProperties;

    /**
     * 发送模板消息
     *
     * @param data
     * @return
     */
    @Override
    public TemplateMsgResult uploadNewsTemplate(String data) {
        try {
            // 获取access_token
            String access_token = WechatConstant.ACCESS_TOKEN;
            TreeMap<String, String> params = new TreeMap<>();
            params.put("access_token", access_token);
            String result = HttpUtil.HttpsDefaultExecute(SystemConstant.POST_METHOD, WechatConstant.SEND_TEMPLATE_MESSAGE_URL,
                    params, data, null);
            TemplateMsgResult templateMsgResult = JsonUtil.fromJsonString(result, TemplateMsgResult.class);

            if (!StringUtils.isEmpty(templateMsgResult.getErrmsg())) {
                if (!OK.equals(templateMsgResult.getErrmsg())) {
                    log.error(templateMsgResult.getErrmsg());
                }
            }
            return templateMsgResult;
        } catch (NullPointerException e) {
            log.error("uploadNewsTemplate:" + e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("uploadNewsTemplate:" + e.getMessage());
            return null;
        }
    }

    /**
     * 根据微信编号和数据-发送模板消息
     *
     * @param wxNumber
     * @param data
     * @return
     */
    @Override
    public TemplateMsgResult send(String wxNumber, Map<String, String> data) {
        if (StringUtils.isEmpty(wxNumber)) {
            TemplateMsgResult result = new TemplateMsgResult();
            result.setErrmsg("没有绑定公众号！");
            log.info("没有绑定公众号！");
            return result;
        }
        TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
        params.put(WechatConstant.FIRST, WeChatTemplateMsg.item(data.get(WechatConstant.FIRST), "#000000"));
        params.put(WechatConstant.KEYWORD1, WeChatTemplateMsg.item(data.get(WechatConstant.KEYWORD1), "#000000"));
        params.put(WechatConstant.KEYWORD2, WeChatTemplateMsg.item(data.get(WechatConstant.KEYWORD2), "#000000"));
        params.put(WechatConstant.KEYWORD3, WeChatTemplateMsg.item(data.get(WechatConstant.KEYWORD3), "#FF0000"));
        params.put(WechatConstant.REMARK, WeChatTemplateMsg.item(data.get(WechatConstant.REMARK), "#000000"));
        WeChatTemplateMsg wechatTemplateMsg = new WeChatTemplateMsg();
        wechatTemplateMsg.setTemplate_id(weChatProperties.getTemplateMessageId());
        wechatTemplateMsg.setTouser(wxNumber);
        wechatTemplateMsg.setData(params);
        String dataStr = JsonUtil.toJsonString(wechatTemplateMsg);
        return uploadNewsTemplate(dataStr);
    }

    /**
     * @param wxNumber
     * @param orgName_taxCodes
     * @return
     */
    @Override
    public TemplateMsgResult sendSalesDateReject(String wxNumber, String orgName_taxCodes) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "申报数据有误");
        data.put(WechatConstant.KEYWORD3, String.format("贵司以下税号上传的销售数据有误，请尽快登陆J&P自动计税系统重新上传，以免耽误申报！\n%s", orgName_taxCodes));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 税金确认
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendTaxRecognition(String wxNumber, String orgName_taxCodes) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "待确认税金");
        data.put(WechatConstant.KEYWORD3, String.format("贵司以下税号上传的申报明细已发送，请尽快登陆J&P自动计税系统确认税金，以免耽误申报！\n%s", orgName_taxCodes));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 税金支付
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendTaxPay(String wxNumber, String orgName_taxCodes) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "待支付税金");
        data.put(WechatConstant.KEYWORD3, String.format("贵司以下税号的税金尚未支付，请尽快登陆J&P自动计税系统上传付款截图，以免耽误申报！\n%s", orgName_taxCodes));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 上传销售数据
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @param currentTime      {本年本月5号}
     * @return
     */
    @Override
    public TemplateMsgResult sendSalesData(String wxNumber, String orgName_taxCodes, String currentTime) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "上月销售数据待上传");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("贵司以下税号上月的销售数据尚未提供，请于").append(currentTime);
        stringBuilder.append("前登陆J&P自动计税系统上传，以免耽误申报！\n").append(orgName_taxCodes);
        data.put(WechatConstant.KEYWORD3, stringBuilder.toString());
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }


    /**
     * 申报通知
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendDeclareNotice(String wxNumber, String orgName_taxCodes) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "本月申报通知");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("贵司以下税号本月需要进行申报，请及时登陆J&P自动计税系统进行处理，以免耽误申报！").append("\n");
        stringBuilder.append(orgName_taxCodes);
        data.put(WechatConstant.KEYWORD3, stringBuilder.toString());
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 签字申报单及付税金截图驳回
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendRejectTaxScreenshot(String wxNumber, String orgName_taxCodes) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "付税金截图/签字申报单有误");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("贵司以下税号的付税金截图/签字申报单有误，请尽快登陆J&P自动计税系统重新上传，以免耽误申报！").append("\n");
        stringBuilder.append(orgName_taxCodes);
        data.put(WechatConstant.KEYWORD3, stringBuilder.toString());
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 申报次数不足提醒支付服务费
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendDeclareCount(String wxNumber, String orgName_taxCodes) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "申报费不足");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("贵司以下税号的申报费用即将使用完毕，请尽快登陆J&P自动计税系统进行充值，以免耽误申报！").append("\n");
        stringBuilder.append(orgName_taxCodes);
        data.put(WechatConstant.KEYWORD3, stringBuilder.toString());
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 年度申报剩余申报次数不足
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendAnnualDeclareCount(String wxNumber, String orgName_taxCodes) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "年度申报费用不足");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("年度申报即将开始，贵司以下税号剩余申报次数不足，请及时充值。").append("\n");
        stringBuilder.append(orgName_taxCodes);
        data.put(WechatConstant.KEYWORD3, stringBuilder.toString());
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 未初始化
     *
     * @param wxNumber
     * @param orgNames {中文公司名称A}、{中文公司名称B}、{中文公司名称C}
     * @return
     */
    @Override
    public TemplateMsgResult sendUninitialized(String wxNumber, String orgNames) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "系统待初始化");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("贵司尚未对以下公司进行系统初始化（完善计税依据并上传历史数据），")
                .append("请尽快登录J&P自动计税系统进行处理。并在后续每月按时上传上月销售数据，以免耽误申报！").append("\n");
        stringBuilder.append(orgNames);
        data.put(WechatConstant.KEYWORD3, stringBuilder.toString());
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 销售预警提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @return
     */
    @Override
    public TemplateMsgResult sendSalesWarning(String wxNumber, String orgName, String countryName) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "超过远程额度，需要注册税号");
        data.put(WechatConstant.KEYWORD3, String.format("贵司%s在%s即将到达该国远程销售阈值，按照税法要求需要注册该国税号，请及时注册。", orgName, countryName));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @return
     */
    @Override
    public TemplateMsgResult sendWarehouse(String wxNumber, String orgName, String countryName) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, String.format("%s需要注册税号", countryName));
        data.put(WechatConstant.KEYWORD3, String.format("贵司%s在%s已使用海外仓，按照税法要求需要注册该国税号，请及时注册。", orgName, countryName));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * 待上传注册资料
     *
     * @param wxNumber
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendStayUploadRegistration(String wxNumber, String orgName_countryNames) {
        String format = String.format("贵司以下公司税号的注册资料尚未上传，请尽快登录J&P自动计税系统进行处理，以免耽误注册！\n%s", orgName_countryNames);
        return registrationSend(wxNumber, "待上传注册资料", format);
    }

    /**
     * 注册资料被驳回
     *
     * @param wxNumber
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendReUploadCorrectFile(String wxNumber, String orgName_countryNames) {
        String format = String.format("贵司以下公司税号的注册资料有误，请尽快登录J&P自动计税系统进行修改，以免耽误注册！\n%s", orgName_countryNames);
        return registrationSend(wxNumber, "注册资料有误", format);
    }

    /**
     * POA未回传提醒
     *
     * @param wxNumber
     * @param isoCode
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendStayPassPOA(String wxNumber, String isoCode, String orgName_countryNames) {
        if (VatConstants.ES.equals(isoCode)) {
            String format = String.format("贵司以下公司的西班牙POA需要办理海牙认证或双认证，请尽快登录J&P自动计税系统查看处理，以免耽误注册！\n%s", orgName_countryNames);
            return registrationSend(wxNumber, "待办理认证文件", format);
        } else {
            String format = String.format("贵司以下公司的POA未签字回传，请尽快登录J&P自动计税系统进行处理，以免耽误注册！\n%s", orgName_countryNames);
            return registrationSend(wxNumber, "待回传签字POA", format);
        }
    }

    /**
     * POA/认证文件被驳回提醒
     *
     * @param wxNumber
     * @param isoCode
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */
    @Override
    public TemplateMsgResult sendPoaRejected(String wxNumber, String isoCode, String orgName_countryNames) {
        if (VatConstants.ES.equals(isoCode)) {
            String format = String.format("贵司以下公司的西班牙POA认证文件有误，请尽快登录J&P自动计税系统查看处理，以免耽误注册！\n%s", orgName_countryNames);
            return registrationSend(wxNumber, "POA认证文件有误", format);
        } else {
            String format = String.format("贵司以下公司的POA签字文件有误，请尽快登录J&P自动计税系统重新上传，以免耽误注册！\n%s", orgName_countryNames);
            return registrationSend(wxNumber, "POA签字文件有误", format);
        }
    }

    /**
     * EORI未回传提醒
     *
     * @param wxNumber
     * @param orgName_countryNames
     * @return
     */
    @Override
    public TemplateMsgResult sendStayEori(String wxNumber, String orgName_countryNames) {
        String format = String.format("贵司以下公司的EORI申请表未签字回传，请尽快登录J&P自动计税系统处理，以免耽误注册！\n%s", orgName_countryNames);
        return registrationSend(wxNumber, "待回传EORI申请表", format);
    }

    /**
     * EORI被驳回提醒
     *
     * @param wxNumber
     * @param orgName_countryNames
     * @return
     */
    @Override
    public TemplateMsgResult sendEoriRejected(String wxNumber, String orgName_countryNames) {
        String format = String.format("贵司以下公司的EORI申请表有误，请尽快登录J&P自动计税系统处理，以免耽误注册！\n%s", orgName_countryNames);
        return registrationSend(wxNumber, "EORI申请表有误", format);
    }

    /**
     * EORI下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @param eoriNum
     * @return
     */
    @Override
    public TemplateMsgResult sendFinishEORI(String wxNumber, String orgName, String countryName, String eoriNum) {
        StringBuilder sb = new StringBuilder();
        sb.append("贵司").append(orgName).append("的").append(countryName).append("EORI号注册成功，号码为：");
        sb.append(eoriNum).append("，详情请登录J&P自动计税系统查看。请注意：在哪国清关，缴纳的进口VAT仅能用于抵扣哪国的销售VAT。");
        return registrationSend(wxNumber, "EORI申请成功", sb.toString());
    }

    /**
     * steuemummer number证书下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param steuemummerNumber
     * @return
     */
    @Override
    public TemplateMsgResult sendLocalTaxCode(String wxNumber, String orgName, String steuemummerNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("贵司").append(orgName).append("德国本地税号已申请成功，税号为：");
        sb.append(steuemummerNumber).append("，请登录J&P自动计税系统下载。请注意：本地税号生效后即需要在德国开始正常申报，请留意系统的申报通知。");
        return registrationSend(wxNumber, "德国steuemummer number号码申请成功", sb.toString());
    }

    /**
     * 欧盟证书下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @param euTaxCode
     * @return
     */
    @Override
    public TemplateMsgResult sendEuTaxCode(String wxNumber, String orgName, String countryName, String euTaxCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("贵司").append(orgName).append(countryName).append("的欧盟税号证书已申请成功，税号为：");
        sb.append(euTaxCode).append("，请登录J&P自动计税系统下载并及时添加到销售平台。温馨提示：请每月按时上传销售数据以免耽误申报，谢谢！");
        return registrationSend(wxNumber, String.format("%s欧盟税号证书申请成功", countryName), sb.toString());
    }

    /**
     * 税务证书副本下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @return
     */
    @Override
    public TemplateMsgResult sendDuplicateTaxCert(String wxNumber, String orgName, String countryName) {
        StringBuilder sb = new StringBuilder();
        sb.append("贵司").append(orgName).append(countryName)
                .append("税务证书副本已申请成功，请登录J&P自动计税系统下载并及时添加到销售平台后台。");
        return registrationSend(wxNumber, String.format("%s税务证书副本申请成功", countryName), sb.toString());
    }

    /**
     * 待上传销售数据进行补税
     *
     * @param wxNumber
     * @param orgNames
     * @return
     */
    @Override
    public TemplateMsgResult sendTaxCollection(String wxNumber, String orgNames) {
        String format = String.format("贵司以下公司收到德国税局通知需要进行补税，但当前申报数据不完整，请尽快登录J&P自动计税系统上传，以免耽注册！\n%s", orgNames);
        return registrationSend(wxNumber, "待上传销售数据进行补税", format);
    }

    /**
     * 英国税号下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @return
     */
    @Override
    public TemplateMsgResult sendGbEuTaxCodefile(String wxNumber, String orgName) {
        String format = String.format("贵司%s英国税号已申请成功，请登录J&P自动计税系统查看。如税局未及时激活EORI号码请联系J&P并待VAT生效后再上传到销售平台。如申请FRS需要注意进口货值需达到税局要求方能按低税率申报。", orgName);
        return registrationSend(wxNumber, "英国税号注册成功", format);
    }

    /**
     * 待邮寄POA认证文件/原件邮寄提醒
     *
     * @param wxNumber
     * @param isoCode
     * @param orgNames 公司名或者是公司+国家名称
     * @return
     */
    @Override
    public TemplateMsgResult sendPoaMail(String wxNumber, String isoCode, String orgNames) {
        if (VatConstants.ES.equals(isoCode)) {
            String format = String.format("贵司以下公司的西班牙POA认证文件已审核完毕，请登录J&P自动计税系统查看邮寄信息并上传快递单号，以免耽误注册！\n%s", orgNames);
            return registrationSend(wxNumber, "待邮寄POA认证文件", format);
        } else {
            String format = String.format("贵司以下公司税号的注册资料需要邮件原件给我司，请登录J&P自动计税系统查看邮寄信息并上传快递单号，以免耽误注册！\n%s", orgNames);
            return registrationSend(wxNumber, "待邮寄注册资料原件", format);
        }
    }

    /**
     * 英国已超过FRS销售额上
     *
     * @param wxNumber
     * @param companyName
     * @return
     */
    @Override
    public TemplateMsgResult sendExceedFRS(String wxNumber, String companyName) {
        String format = String.format("%s在英国已超过FRS销售额上限，必须立即退出FRS，请尽快登录系统查看并处理。", companyName);
        return registrationSend(wxNumber, "超过FRS销售额上限", format);
    }

    /**
     * 通用的发送模版
     *
     * @param wxNumber
     * @param keyword2Content
     * @param keyword3Content
     * @return
     */
    private TemplateMsgResult registrationSend(String wxNumber, String keyword2Content, String keyword3Content) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, keyword2Content);
        data.put(WechatConstant.KEYWORD3, keyword3Content);
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }


    /**
     * @param wxNumber
     * @param name
     * @return
     */
    @Override
    public TemplateMsgResult sendChangeStatus(String wxNumber, String name) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "申报信息的状态已修改，请查看");
        data.put(WechatConstant.KEYWORD3, String.format("修改人姓名:" + name));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * @param wxNumber
     * @param name
     * @return
     */
    @Override
    public TemplateMsgResult sendMessageByAdmin(String wxNumber, String name, String typeText) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "您有一条新的消息，请查看");
        data.put(WechatConstant.KEYWORD3, String.format("发送人姓名：" + name + "; 消息类型：" + typeText));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * @param wxNumber
     * @param name
     * @return
     */
    @Override
    public TemplateMsgResult sendMessageForAddVat(String wxNumber, String name) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "您有一条新的消息，请查看");
        data.put(WechatConstant.KEYWORD3, String.format("发送人姓名：" + name + "; 消息类型：新增税号"));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }

    /**
     * @param wxNumber
     * @param name
     * @return
     */
    @Override
    public TemplateMsgResult sendMessageForUpdateVat(String wxNumber, String name) {
        Map<String, String> data = new TreeMap<>();
        data.put(WechatConstant.FIRST, WechatConstant.FIRST_CONTENT);
        data.put(WechatConstant.KEYWORD1, WechatConstant.KEYWORD1_CONTENT);
        data.put(WechatConstant.KEYWORD2, "您有一条新的消息，请查看");
        data.put(WechatConstant.KEYWORD3, String.format("发送人姓名：" + name + "; 消息类型：修改税号"));
        data.put(WechatConstant.REMARK, "");
        return send(wxNumber, data);
    }
}
