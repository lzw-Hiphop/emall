package com.lzw.emall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.lzw.emall.service.OrderService;
import com.lzw.emall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    private final String APP_ID = "2016101300674999";
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCDS5bxyXpMLaFLWTeLfD3rqIQ4oJlSjQ0ka+ESrz47TeEgXnUhHQzxL7bfLBrnqGuVKW6FP0dquVjK/geaHjPwAItM0tlhxnv/13MKpleh7JYHBHipnJoq3WPWMX7MEOmw7TxI0zVonmi95LN4dTjBtdQ4qL1N40kjleOlkZkJ0fieGo05rXYLdk3MIkpC1ox23hvn8wtLm4GbtdRiC5gOodVV162QPRVTPESeozWyZBmHPAJB9MveqkljuIJADQb/vic5DY2HC4ov84dsZyOOH7HUy+1zsmdQJobLHEmr07J0pYxuwumZAxqKv7zMkJ2lnXSCGcnhN/Xf9gYjANAFAgMBAAECggEAY0mOZFoQefsntmtJbdoOmV9eXdRVHfA8uAY8tnx/eylxzaCgwuHNH7cNe8dGsVjTwoEKawlnYHW5iAiChzG/5QcEUJxCd4Y5z0eGX89bBcB70+K1vM4IZ9Hz5c6YLIm9r2Y36HqQMQVaAAArGDTNLu/qBM7WGUh/zsFS3ao1KQ7gSmYicRvtwfmwMrnVuymTWX5q/6npAHQHu2WGU13qf7DAj47is2GTKfAbX7iQXX4KXSoAZ2uLj4gA1Vj7f8Y2/z5Iy6GD8kpANNxv990z1MSHxbqv9KSaDInCjw7jKT19kLAKmc6DMp9piVMuJKXPXCijG2LsHGn+jfrGmHNNAQKBgQDRgA9l1kXLiv6rRoql18WHtbzmU688zyeAUtFyQvmh0W86/Irg/ZsfCmQ+aXSKKDrOtlbMZqc7Izid7DFYJn1OqBQmY1QZ6Qf/6Ld6Nbl3zFAT4XH5crsFVxm3CjSMZVjDl/Eio+NK+3Py2SFIpnAOzrXYhetXYEghWGL2oEUjXQKBgQCgb99d+xI27i85fuMt9XaEYyaO47Rzj9lpjBk6MrfjKO6qYXwk883FyzP7tmGJ/rPEvtsOrKtlXih71ZzQTLYTJMRzQAnJd9cQIVEaCwOQTtjEc9rvqxJ/bkP7t7lSSaikp34DrBTmuCh8UiMQZQBYnNhSx7XopsH8p60SI4N8yQKBgHMnV89bKvwJh5gvaxBjfODuhUl5IC5lk0nWu+d5ZuuKSgB7Rf8LhxNVy+n+Xx99o21yDl3F4w//eSRc6naQmXn9qy26lUS0sT/587gKBcsSk8WJuvMpHCGAUdMpmd696j+AqfunZ80UUBRhQwlC6v9Ioe3FFqntc1hY0/TJ/ZbFAoGBAI0ZltMqyxwwYqwoYli7Nt677N9ieAPikY14P3+lt4A7MZv7XJmLKoA4dKF+B068CRKR4EqJpbvjEavHdMDlloRDo9rQiUr88NIo2xESerHiBlbQpc5/ICeerH+nV399dKpK/FLydvdrI4AjJN94VNOaLc6wAdZ5QsaYGWiIf02xAoGBAL+U/Qe3x/soDgbEWZlKrGJ/d6N+0Qiwdyset3fI8C+uKlvfcKomkSuT+mM+kUuCSyGVAOOxMT41BTqptyJsdT0xHs6q3mo+/10YfKSpsSkeYVxCxYVziWv3s+OWY17vdMjIwkiwZAGdQnCa6KSICBnexmC0Zm4sNwRi9rOL3r1J";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgU0yGerV+hYDoPD9g2XR7U7+lZZiEnsGRdo5246jg8CIhYj+qLgbS7VQK2+nLjlUPFsC90ueK2Qj147kR3VMzGAKsHa7BKcRiSzfAf601+JZFSIN9idQyh3HAMSGsSz8lGZn6OKEq+ovmU2bp74VKSC6I2PxUKYq14/JM4MdqJoZBc9+kjjlRGre3YeLB0A1iCIRh5mJ+LLHhchxRTyNMG0U+VD1YJuAAB8kVgYIW5HXR86i44y+FNBlChcFP0oMNXwshnjUZwh9d6gTwFG/iprwzdzQrkQQBNw3kFSGQ+ykFSTAu8eNm3zHLgtkdUc4eBTlW6dRQqkf9oHsPGqPiQIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://120.79.244.203:8080/EMall/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://127.0.0.1:8080/returnUrl";


    @RequestMapping("/alipay")
    public void alipay(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {

        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = httpRequest.getParameter("orderid");
        //付款金额，必填
        String total_amount =httpRequest.getParameter("total");
        //订单名称，必填
        String subject = new String(httpRequest.getParameter("name").getBytes("ISO-8859-1"), "utf-8")+"的订单";
        //商品描述，可空
        String body = "尊敬的会员欢迎购买该店商品!";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name =  iter.next();
            String[] values =  requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){

            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            //支付成功，改变支付状态
            orderService.payState(out_trade_no);
            return "redirect:/paySuccess";//跳转付款成功页面
        }else{
            return "pay_fail";//跳转付款失败页面
        }

    }


}


