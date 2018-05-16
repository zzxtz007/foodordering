package com.qiancheng.om.common.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.*;
import java.util.*;

/**
 * @author XLY
 */
public class WeChatPayment {

    private static final Logger LOGGER = Logger.getLogger(WeChatPayment.class);
    private static final PropertiesHandler PROP = new PropertiesHandler("order_meal.properties");

    private static String getPrepayUrl = PROP.getValue("wechat.payment.getPrepayUrl");
    private static String orderQueryUrl = PROP.getValue("wechat.payment.orderQueryUrl");
    private static String orderRefundUrl = PROP.getValue("wechat.payment.orderRefundUrl");
    private static String orderRefundQueryUrl = PROP.getValue("wechat.payment.orderRefundQueryUrl");
    private static String apiKey = PROP.getValue("wechat.payment.apiKey");
    private static String appId = PROP.getValue("wechat.payment.appId");
    private static String mchId = PROP.getValue("wechat.payment.mchId");
    private static String notifyUrl = PROP.getValue("wechat.payment.notifyUrl");
    private static String tradeType = PROP.getValue("wechat.payment.tradeType");
    private static String spbillCreateIp = PROP.getValue("wechat.payment.spbillCreateIp");
    private static String body = PROP.getValue("wechat.payment.body");
    private static String signType = PROP.getValue("wechat.payment.signType");
    private static BigDecimal rates = new BigDecimal(PROP.getValue("wechat.payment.rates"));
    private static String path = AppContextUtils.getClasspath(PROP.getValue("wechat.payment.path"));


    /**
     * 扣除微信支付所需的费率
     * @param initialPrice 原所得
     * @return 扣除费率后的所得
     */
    public static BigDecimal deductionWeChatRates  (BigDecimal initialPrice) {
        initialPrice = initialPrice.setScale(8, BigDecimal.ROUND_DOWN);
        return (initialPrice.multiply(rates)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取包含调用微信支付的数据
     *
     * @param prepayId 预支付订单
     * @return 包含调用微信支付数据的 map 集合
     */
    public static Map<String, String> getPayment(String prepayId) {
        Map<String, String> payment = new TreeMap<>();
        payment.put("appId", appId);
        payment.put("nonceStr", getRandomString());
        payment.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        payment.put("package", "prepay_id=" + prepayId);
        payment.put("signType", signType);
        payment.put("paySign", createSign(payment));

        LOGGER.info("微信支付数据" + payment);
        return payment;
    }

    /**
     * 输出调起微信支付的数据
     *
     * @param message 预下订单需要的数据
     * @return 包含微信发起支付所需的数据
     */
    public static Map<String, String> outgoingPaymentData(Map<String, String> message) {
        Map<String, String> prepayMessage = getPrepayId(message);
        if (prepayMessage.get("prepay_id") == null) {
            return prepayMessage;
        }
        String prepayId = prepayMessage.get("prepay_id");
        Map<String, String> paymentInfo = getPayment(prepayId);
        paymentInfo.put("prepayId", prepayId);


        LOGGER.info("预支付 Id" + paymentInfo);
        return paymentInfo;
    }

    /**
     * 查询订单支付状态
     *
     * @param orderId 订单号
     * @return map 集合
     */
    public static Map<String, String> getPaymentStatus(String orderId) {
        Map<String, String> parameters = new TreeMap<>();
        parameters.put("appid", appId);
        parameters.put("mch_id", mchId);
        parameters.put("nonce_str", getRandomString());
        parameters.put("out_trade_no", orderId);
        parameters.put("sign", createSign(parameters));
        String requestXml = getRequestXml(parameters);
        String responseXml = getResponseXml(requestXml, orderQueryUrl);

        LOGGER.info("订单支付状态反馈" + responseXml);
        return castToMap(responseXml);
    }

    /**
     * 申请订单退款
     *
     * @param orderId  订单号
     * @param totalFee 订单价格
     * @return map 集合
     */
    public static Map<String, String> refundOrder(String orderId, String totalFee) {
        Map<String, String> parameters = new TreeMap<>();
        parameters.put("appid", appId);
        parameters.put("mch_id", mchId);
        parameters.put("total_fee", totalFee);
        parameters.put("refund_fee", totalFee);
        parameters.put("nonce_str", getRandomString());
        parameters.put("out_trade_no", orderId);
        parameters.put("out_refund_no", orderId);
        parameters.put("sign", createSign(parameters));
        String requestXml = getRequestXml(parameters);
        String responseXml = null;
        try {
            responseXml = refund(requestXml);
        } catch (Exception e) {
            LOGGER.error("申请订单退款失败", e);
        }
        LOGGER.info("退款请求信息" + requestXml);
        LOGGER.info("退款反馈信息" + responseXml);
        return castToMap(responseXml);
    }

    /**
     * 查询订单退款
     *
     * @param orderId 订单号
     * @return map 集合
     */
    public static Map<String, String> queryRefundOrder(String orderId) {
        Map<String, String> parameters = new TreeMap<>();
        parameters.put("appid", appId);
        parameters.put("mch_id", mchId);
        parameters.put("nonce_str", getRandomString());
        parameters.put("out_refund_no", orderId);
        parameters.put("sign", createSign(parameters));
        String requestXml = getRequestXml(parameters);
        String responseXml = getResponseXml(requestXml, orderRefundQueryUrl);
        return castToMap(responseXml);
    }


    /**
     * 获取预订单 ID
     *
     * @param message 包含下单所需的所有信息的 map
     * @return 返回值 map
     */
    private static Map<String, String> getPrepayId(Map<String, String> message) {
        Map<String, String> parameters = new TreeMap<>();
        parameters.put("appid", appId);
        parameters.put("mch_id", mchId);
        parameters.put("nonce_str", getRandomString());
        parameters.put("body", body);
        parameters.put("out_trade_no", String.valueOf(message.get("orderId")));
        parameters.put("total_fee", String.valueOf(message.get("totalFee")));
        parameters.put("notify_url", notifyUrl);
        parameters.put("trade_type", tradeType);
        parameters.put("spbill_create_ip", spbillCreateIp);
        parameters.put("openid", String.valueOf(message.get("openId")));
        parameters.put("sign", createSign(parameters));
        String requestXml = getRequestXml(parameters);
        String responseXml = getResponseXml(requestXml, getPrepayUrl);
        LOGGER.info("预支付信息" + responseXml);
        return castToMap(responseXml);
    }

    /**
     * 获取随机字符串方法
     *
     * @return 生成好的随机字符串
     */
    private static String getRandomString() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 将 xml 格式的字符串转换成 map 类型
     *
     * @param xmlStr xml 格式的字符串
     * @return 包含信息的 map
     */
    private static Map<String, String> castToMap(String xmlStr) {
        Map<String, String> map = new HashMap<>();
        try {
            org.dom4j.Document document = DocumentHelper.parseText(xmlStr);
            org.dom4j.Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            for (Object aNode : node) {
                Element elm = (Element) aNode;
                map.put(elm.getName(), elm.getText());
            }
        } catch (DocumentException e) {
            LOGGER.error("String 转成 XML 失败", e);
        }
        return map;
    }

    /**
     * 创建签名
     *
     * @param parameters 包含签名所需的所有数据
     * @return 生成后的签名
     */
    private static String createSign(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sign = new StringBuilder();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            Set set = parameters.entrySet();
            for (Object aSet : set) {
                @SuppressWarnings("unchecked")
                Map.Entry<String, String> entry = (Map.Entry<String, String>) aSet;
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null && !value.equals("") && !key.equals("sign") && !key.equals
                        ("key")) {
                    sb.append(key).append("=").append(value).append("&");
                }
            }
            sb.append("key=").append(apiKey);

            byte [] bytes = sb.toString().getBytes("UTF-8");
            md.reset();
            md.update(bytes);
            bytes = md.digest();

            // 防止签名丢 0e
            for (byte b : bytes){
                String s = Integer.toHexString(b & 0xFF);
                if (s.length() == 1) {
                    s = "0" + s;
                }
                sign.append(s);
            }

            return String.valueOf(sign);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("创建签名失败", e);
        }

        return null;
    }

    /**
     * 将统一下单的参数转换成 xml 格式的字符串
     *
     * @param parameters 请求所需的参数
     * @return 字符串
     */
    private static String getRequestXml(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        Set set = parameters.entrySet();
        for (Object aSet : set) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, String> entry = (Map.Entry<String, String>) aSet;
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("<").append(key).append(">").append(value).append("</").append
                    (key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 获取响应的 xml 格式的参数
     *
     * @param requestXml  请求内的 xml 格式的参数
     * @param responseUrl 发出请求的 url
     * @return 字符串
     */
    private static String getResponseXml(String requestXml, String responseUrl) {
        try {
            URL url = new URL(responseUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(30000);
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(requestXml.getBytes("UTF-8"));
            outputStream.close();

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            StringBuilder responseXml = new StringBuilder();
            String str;
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while (null != (str = bufferedReader.readLine())) {
                responseXml.append(str);
            }
            bufferedReader.close();
            inputStream.close();
            outputStream.close();
            inputStreamReader.close();
            urlConnection.disconnect();
            return responseXml.toString();
        } catch (IOException e) {
            LOGGER.error("获取响应失败", e);
            return "";
        }
    }

    /**
     * 申请退款
     * @param body 申请退款的 xml 格式
     * @return 返回的字符串
     * @throws Exception 抛出异常
     */
    private static String refund(String body) throws Exception {
        String result;
        //商户id
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        File cert = new File(path);
        FileInputStream instream = new FileInputStream(cert);
        //指定PKCS12的密码(商户ID)
        keyStore.load(instream, mchId.toCharArray());
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray
                ()).build();
        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new
                String[]{"TLSv1"}, null, SSLConnectionSocketFactory
                .BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //设置httpclient的SSLSocketFactory
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httppost = new HttpPost(orderRefundUrl);
            StringEntity reqEntity = new StringEntity(body, "UTF-8");
            httppost.setEntity(reqEntity);

            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (Exception e) {
                LOGGER.error("获取退款信息失败", e);
                throw new RuntimeException(e);
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            LOGGER.error("与微信接口通信失败", e);
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("微信端口退款信息" + result);
        return result;
    }

}
