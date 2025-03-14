package com.sky.controller.nofity;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.OrderService;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 支付回调相关接口
 */
@RestController
@RequestMapping("/notify")
@Slf4j
public class PayNotifyController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理微信支付成功的回调通知
     * @param request
     * @param response
     * @throws Exception
     */
    public void paySuccessNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 读取数据
        String body = readData(request);
        log.info("支付成功回调接口，接收到的数据：{}", body);

        // 数据解密
        String plainText = decryptData(body);
        log.info("解密后的数据：{}", plainText);

        JSONObject jsonObject = JSON.parseObject(plainText);
        String outTradeNo = jsonObject.getString("out_trade_no");// 商户平台订单号
        String transactionId = jsonObject.getString("transaction_id"); // 微信支付交易号

        log.info("商户平台订单号：{}", outTradeNo);
        log.info("微信支付交易号：{}", transactionId);

        // 业务处理：修改订单状态、来单提醒
        orderService.paySuceess(outTradeNo);

        // 给微信响应
        responseToWeixin(response);

    }

    /**
     * 处理微信退款成功的回调通知
     *
     * @param requestBody 微信回调的请求体
     * @return 返回给微信的处理结果
     */
    @PostMapping("/refundsuccess")
    public void refundsuccess(@RequestBody String requestBody, HttpServletResponse response) throws Exception {
        // 解析微信回调的请求体，获取退款相关信息
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String outRefundNo = jsonObject.getString("out_refund_no"); // 商户退款单号
        String outTradeNo = jsonObject.getString("out_trade_no"); // 商户订单号
        String refundStatus = jsonObject.getString("refund_status"); // 退款状态

        // 根据退款状态处理业务逻辑
        switch (refundStatus) {
            case "SUCCESS":
                // 退款成功，更新订单支付状态
                Orders orders = new Orders();
                orders.setNumber(outTradeNo);
                orders.setPayStatus(Orders.REFUND);
                orders.setStatus(Orders.CANCELLED); // 微信支付回调是一个异步过程，可能会在订单状态被修改后触发，因此需要在回调中再次确认和更新订单状态，以确保订单状态与支付状态一致。
                // 直接调用OrderMapper更新订单支付状态
                orderMapper.update(orders);
                log.info("退款成功，订单号：{}，退款单号：{}", outTradeNo, outRefundNo);
                break;
            case "FAIL":
                // 退款失败，记录日志
                log.error("退款失败，订单号：{}，退款单号：{}", outTradeNo, outRefundNo);
                break;
            case "PROCESSING":
                // 退款处理中，记录日志
                log.info("退款处理中，订单号：{}，退款单号：{}", outTradeNo, outRefundNo);
                break;
            case "ABNORMAL":
                // 退款异常，记录日志
                log.warn("退款异常，订单号：{}，退款单号：{}", outTradeNo, outRefundNo);
                break;
            default:
                // 其他状态，记录日志
                log.warn("未知的退款状态：{}，订单号：{}，退款单号：{}", refundStatus, outTradeNo, outRefundNo);
                break;
        }

        // 返回成功响应给微信
        responseToWeixin(response);
    }

    /**
     * 读取数据
     * @param request
     * @return
     * @throws Exception
     */
    private String readData(HttpServletRequest request) throws Exception {
        BufferedReader reader = request.getReader();
        StringBuilder result = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (result.length() > 0) {
                result.append("\n");
            }
            result.append(line);
        }
        return result.toString();
    }

    /**
     * 数据解密
     * @param body 通知数据
     * @return
     * @throws Exception
     */
    private String decryptData(String body) throws Exception {
        // 解析通知数据
        JSONObject resultObject = JSON.parseObject(body);
        JSONObject resource = resultObject.getJSONObject("resource");// 通知数据:通知资源数据。
        String ciphertext = resource.getString("ciphertext");// 数据密文:Base64编码后的回调数据密文，商户需Base64解码并使用APIV3密钥解密
        String nonce = resource.getString("nonce");// 随机串:参与解密的随机串
        String associatedData = resource.getString("associated_data"); // 附加数据:参与解密的附加数据，该字段可能为空


        AesUtil aesUtil = new AesUtil(weChatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        // 密文解密
        String plainText = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
                nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
        return plainText;
    }

    /**
     * 给微信响应
     * @param response
     */
    private void responseToWeixin(HttpServletResponse response) throws Exception {
        response.setStatus(200);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        map.put("message", "SUCCESS");
        response.setHeader("Content-type", ContentType.APPLICATION_JSON.toString());
        response.getOutputStream().write(JSONUtils.toJSONString(map).getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }
}
