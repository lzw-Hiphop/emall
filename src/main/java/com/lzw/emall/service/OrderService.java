package com.lzw.emall.service;

import com.lzw.emall.bean.Cart;
import com.lzw.emall.bean.Orders;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    public void sessionToCart(HttpServletRequest request);
    public List<Cart> order(HttpServletRequest request);
    public Orders confirmOrder(HttpServletRequest request);
    public void payState(String orderid);
    public void deleteById(HttpServletRequest request);
    public Orders seleteOrderById(String orderid);
}
