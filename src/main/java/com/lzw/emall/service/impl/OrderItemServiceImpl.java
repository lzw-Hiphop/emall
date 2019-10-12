package com.lzw.emall.service.impl;

import com.lzw.emall.bean.*;
import com.lzw.emall.bean.extend.OrderExt;
import com.lzw.emall.mapper.OrderitemMapper;
import com.lzw.emall.mapper.OrdersMapper;
import com.lzw.emall.mapper.ProductMapper;
import com.lzw.emall.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderitemMapper orderitemMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<List<OrderExt>> SelectOrderitem(HttpServletRequest request){
        OrdersExample ordersExample = new OrdersExample();
        ordersExample.createCriteria().andUserIdEqualTo((int)request.getSession().getAttribute("userid"));
        List<Orders> orders = ordersMapper.selectByExample(ordersExample);
        List<List<OrderExt>> lists = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            List<OrderExt> list = new ArrayList<>();
            OrderitemExample orderitemExample = new OrderitemExample();
            orderitemExample.createCriteria().andOrderIdEqualTo(orders.get(i).getOrderId());
            List<Orderitem> orderitems = orderitemMapper.selectByExample(orderitemExample);
            for (int j = 0; j < orderitems.size(); j++) {
                OrderExt orderExt = new OrderExt();
                orderExt.setItemId(orderitems.get(j).getItemId());
                SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                String time = df.format(orders.get(i).getOrderTime());
                orderExt.setOrderTime(time);
                Product product = productMapper.selectByPrimaryKey(orderitems.get(j).getProductId());
                orderExt.setPname(product.getProductName());
                orderExt.setPimage(product.getProductImage());
                orderExt.setPrice(product.getProductPrice());
                orderExt.setCount(orderitems.get(j).getCount());
                orderExt.setSubtotal(orderitems.get(j).getSubtotal());
                orderExt.setOrderId(orders.get(i).getOrderId());
                orderExt.setPayState(orders.get(i).getPayState());
                list.add(orderExt);
            }
            lists.add(list);
        }
        return lists;
    }
}
