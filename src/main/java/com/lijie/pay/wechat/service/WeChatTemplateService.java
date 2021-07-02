/**
 * @ClassName: TemplateService
 * @Description: TODO
 * @author: cc
 * @date: 2018年8月20日 下午2:49:18
 */
package com.lijie.pay.wechat.service;


import com.lijie.pay.wechat.TemplateMsgResult;

import java.util.Map;

public interface WeChatTemplateService {

    /**
     * 发送模板消息
     *
     * @param data
     * @return
     */
    TemplateMsgResult uploadNewsTemplate(String data);

    /**
     * 根据微信编号和数据-发送模板消息
     *
     * @param wxNumber
     * @param data
     * @return
     */
    TemplateMsgResult send(String wxNumber, Map<String, String> data);

    /**
     * 销售数据驳回
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendSalesDateReject(String wxNumber, String orgName_taxCodes);

    /**
     * 税金确认
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendTaxRecognition(String wxNumber, String orgName_taxCodes);

    /**
     * 税金支付
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendTaxPay(String wxNumber, String orgName_taxCodes);

    /**
     * 上传销售数据
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @param currentTime      {本年本月5号}
     * @return
     */
    TemplateMsgResult sendSalesData(String wxNumber, String orgName_taxCodes, String currentTime);

    /**
     * 申报通知
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendDeclareNotice(String wxNumber, String orgName_taxCodes);

    /**
     * 签字申报单及付税金截图驳回
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendRejectTaxScreenshot(String wxNumber, String orgName_taxCodes);

    /**
     * 申报次数不足提醒支付服务费
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendDeclareCount(String wxNumber, String orgName_taxCodes);

    /**
     * 年度申报剩余申报次数不足
     *
     * @param wxNumber
     * @param orgName_taxCodes {中文公司名称A}{税号编码1}+{税号编码2}+{该公司名下所有需要有该通知展示的税号编码}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendAnnualDeclareCount(String wxNumber, String orgName_taxCodes);

    /**
     * 未初始化
     *
     * @param wxNumber
     * @param orgNames {中文公司名称A}、{中文公司名称B}、{中文公司名称C}
     * @return
     */
    TemplateMsgResult sendUninitialized(String wxNumber, String orgNames);

    /**
     * 销售预警提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @return
     */
    TemplateMsgResult sendSalesWarning(String wxNumber, String orgName, String countryName);

    /**
     * 使用货仓
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @return
     */
    TemplateMsgResult sendWarehouse(String wxNumber, String orgName, String countryName);

    /**
     * 待上传注册资料
     *
     * @param wxNumber
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendStayUploadRegistration(String wxNumber, String orgName_countryNames);

    /**
     * 注册资料被驳回
     *
     * @param wxNumber
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendReUploadCorrectFile(String wxNumber, String orgName_countryNames);

    /**
     * POA/认证文件未回传提醒
     *
     * @param wxNumber
     * @param isoCode
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */

    TemplateMsgResult sendStayPassPOA(String wxNumber, String isoCode, String orgName_countryNames);

    /**
     * POA/认证文件被驳回提醒
     *
     * @param wxNumber
     * @param isoCode
     * @param orgName_countryNames {中文公司名称A}{有需要上传注册资料的国家1}+{有需要上传注册资料的国家2}+{该公司名下所有需要有该通知展示的国家}、{中文公司名称B}
     * @return
     */
    TemplateMsgResult sendPoaRejected(String wxNumber, String isoCode, String orgName_countryNames);

    /**
     * EORI未回传提醒
     *
     * @param wxNumber
     * @param orgName_countryNames
     * @return
     */
    TemplateMsgResult sendStayEori(String wxNumber, String orgName_countryNames);

    /**
     * EORI被驳回提醒
     *
     * @param wxNumber
     * @param orgName_countryNames
     * @return
     */
    TemplateMsgResult sendEoriRejected(String wxNumber, String orgName_countryNames);

    /**
     * EORI下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @param eoriNum
     * @return
     */
    TemplateMsgResult sendFinishEORI(String wxNumber, String orgName, String countryName, String eoriNum);

    /**
     * steuemummer number证书下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param steuemummerNumber
     * @return
     */
    TemplateMsgResult sendLocalTaxCode(String wxNumber, String orgName, String steuemummerNumber);

    /**
     * 欧盟证书下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @param euTaxCode
     * @return
     */
    TemplateMsgResult sendEuTaxCode(String wxNumber, String orgName, String countryName, String euTaxCode);

    /**
     * 税务证书副本下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @param countryName
     * @return
     */
    TemplateMsgResult sendDuplicateTaxCert(String wxNumber, String orgName, String countryName);

    /**
     * 待上传销售数据进行补税
     *
     * @param wxNumber
     * @param orgNames
     * @return
     */
    TemplateMsgResult sendTaxCollection(String wxNumber, String orgNames);

    /**
     * 英国税号下发提醒
     *
     * @param wxNumber
     * @param orgName
     * @return
     */
    TemplateMsgResult sendGbEuTaxCodefile(String wxNumber, String orgName);

    /**
     * 待邮寄POA认证文件/原件邮寄提醒
     *
     * @param wxNumber
     * @param isoCode
     * @param orgNames 公司名或者是公司+国家名称
     * @return
     */
    TemplateMsgResult sendPoaMail(String wxNumber, String isoCode, String orgNames);

    /**
     * 英国已超过FRS销售额上
     *
     * @param wxNumber
     * @param companyName
     * @return
     */
    TemplateMsgResult sendExceedFRS(String wxNumber, String companyName);

    /**
     * 状态改变发送消息
     */
    public TemplateMsgResult sendChangeStatus(String wxNumber, String name);

    /**
     * 管理员主动发送消息
     */
    public TemplateMsgResult sendMessageByAdmin(String wxNumber, String name, String typeText);

    /**
     * 管理员新增了一个税号要发消息
     */
    public TemplateMsgResult sendMessageForAddVat(String wxNumber, String name);

    /**
     * 管理员修改了一个税号要发消息
     */
    public TemplateMsgResult sendMessageForUpdateVat(String wxNumber, String name);
}
