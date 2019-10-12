package com.lzw.emall.controller;

import com.lzw.emall.bean.Cart;
import com.lzw.emall.bean.Orders;
import com.lzw.emall.bean.Product;
import com.lzw.emall.bean.extend.OrderExt;
import com.lzw.emall.service.CartService;
import com.lzw.emall.service.OrderItemService;
import com.lzw.emall.service.OrderService;
import com.lzw.emall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;


    @RequestMapping("/order")
    public String order(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("userid")==null){
            return "redirect:/tologin";
        }
        List<Cart> order = orderService.order(request);
        //将订单放进session中
        request.getSession().setAttribute("order",order);
        //计算总价
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < order.size(); i++) {
            sum = sum.add(order.get(i).getSubtotal());
        }

        model.addAttribute("sum",sum);
        model.addAttribute("order",order);
        return "order_confirm";
    }

    @RequestMapping("/orderDeleteById")
    public String deleteById(HttpServletRequest request){
        cartService.deleteById(request);
        return "redirect:/order";
    }

    @RequestMapping("/orderUpdateById")
    public String updateById(HttpServletRequest request){
        cartService.updateById(request);
        return "redirect:/order";
    }

    @RequestMapping("/confirmOrder")
    public String confirmOrder(HttpServletRequest request,RedirectAttributes attr){
        //将订单写入数据库
        Orders order = orderService.confirmOrder(request);
        attr.addAttribute("total", order.getTotalPrice());//跳转地址带上total参数即总价
        attr.addAttribute("orderid", order.getOrderId());//订单号
        attr.addAttribute("name",order.getRealName());//名字
        if(productService.changeStore(order.getOrderId())==false){//减库存
            return "pay_fail";
        }
        //去支付
        return "redirect:/alipay";
    }

    @RequestMapping("/toPay")
    public String toPay(HttpServletRequest request,RedirectAttributes attr){
        String orderid = request.getParameter("orderid");
        Orders order = orderService.seleteOrderById(orderid);
        attr.addAttribute("total", order.getTotalPrice());//跳转地址带上total参数即总价
        attr.addAttribute("orderid", order.getOrderId());//订单号
        attr.addAttribute("name",order.getRealName());//名字
        if(productService.changeStore(order.getOrderId())==false){//减库存
            return "pay_fail";
        }
        return "redirect:/alipay";
    }

    //成功付款
    @RequestMapping("/paySuccess")
    public String paySuccess(HttpServletRequest request,Model model){
        //定义一个记录历史浏览记录商品的集合
        List<Product> historyProductList=new ArrayList<>();
        int[] pids = new int[6];
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if ("pids".equals(cookie.getName())) {
                String[] split = cookie.getValue().split("-");
                for (int i = 0; i < split.length&&i<6; i++) {
                    pids[i] = Integer.parseInt(split[i]);
                    Product product = productService.selectByPrimaryKey(pids[i]);
                    historyProductList.add(product);
                }
            }
        }
        //历史记录
        model.addAttribute("historyProductList",historyProductList);
        return "pay_success";
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(HttpServletRequest request){
        orderService.deleteById(request);
        return "redirect:/allOrder";
    }

    //我的订单
    @RequestMapping("/allOrder")
    public String selectOrders(HttpServletRequest request,Model model){
        if(request.getSession().getAttribute("userid")==null){
            return "redirect:/tologin";
        }
        List<List<OrderExt>> lists = orderItemService.SelectOrderitem(request);
        model.addAttribute("lists",lists);
        return "orders";
    }



}
