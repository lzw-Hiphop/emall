package com.lzw.emall.service;

import com.lzw.emall.bean.extend.OrderExt;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderItemService {
    public List<List<OrderExt>> SelectOrderitem(HttpServletRequest request);
}
