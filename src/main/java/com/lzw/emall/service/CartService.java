package com.lzw.emall.service;

import com.lzw.emall.bean.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CartService {
    public void addToCart(HttpServletRequest request);
    public List<Cart> cart(HttpServletRequest request);
    public void deleteById(HttpServletRequest request);
    public void updateById(HttpServletRequest request);
    public void deleteByUserId(HttpServletRequest request);
}
