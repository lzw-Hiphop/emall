package com.lzw.emall.service.impl;

import com.lzw.emall.bean.Cart;
import com.lzw.emall.bean.CartExample;
import com.lzw.emall.bean.Orderitem;
import com.lzw.emall.bean.Orders;
import com.lzw.emall.mapper.CartMapper;
import com.lzw.emall.mapper.OrderitemMapper;
import com.lzw.emall.mapper.OrdersMapper;
import com.lzw.emall.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderitemMapper orderitemMapper;

    @Override
    public void sessionToCart(HttpServletRequest request){
        List<Cart> carts = (List<Cart>)request.getSession().getAttribute("carts");
        for (int i = 0; i < carts.size(); i++) {
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andPidEqualTo(carts.get(i).getPid()).andUserIdEqualTo((int)request.getSession().getAttribute("userid"));
            if(cartMapper.selectByExample(cartExample).size()!=0){//购物车中存在该商品
                Cart c = cartMapper.selectByExample(cartExample).get(0);
                c.setCount(carts.get(i).getCount()+c.getCount());
                BigDecimal bigDecimal = new BigDecimal(c.getCount()*c.getBaseprice().intValue());
                c.setSubtotal(bigDecimal);
                cartMapper.updateByPrimaryKey(c);
            }else {
                Cart cart = carts.get(i);
                cart.setCartId(null);
                cart.setUserId((int)request.getSession().getAttribute("userid"));
                cartMapper.insert(cart);
            }
            carts.remove(i);
        }
        request.getSession().setAttribute("carts",carts);
    }

    @Override
    public List<Cart> order(HttpServletRequest request){
        String[] pids = request.getParameterValues("pid");
        List<Cart> cartList = new ArrayList<>();
        for (int i = 0; i < pids.length; i++) {
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andUserIdEqualTo((int)request.getSession().getAttribute("userid")).
                    andPidEqualTo(Integer.parseInt(pids[i]));
            cartList.add(cartMapper.selectByExample(cartExample).get(0));
            //删除购物车中下订单的商品
            CartExample example = new CartExample();
            example.createCriteria().andPidEqualTo(Integer.parseInt(pids[i]));
            cartMapper.deleteByExample(example);
        }
        return cartList;
    }

    @Override
    public Orders confirmOrder(HttpServletRequest request){
        String uuid = UUID.randomUUID()+"";
        //写入orders
        Orders order = new Orders();
        String realname = request.getParameter("realname");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String temp = request.getParameter("totalprice");
        BigDecimal totalprice = new BigDecimal(temp);
        order.setOrderId(uuid);
        order.setOrderTime(new Date());
        order.setTotalPrice(totalprice);
        order.setPayState(0);
        order.setRealName(realname);
        order.setAddress(address);
        order.setTelephone(telephone);
        order.setUserId((int)request.getSession().getAttribute("userid"));
        ordersMapper.insertSelective(order);
        //写入orderitem
        List<Cart> carts = (List<Cart>)request.getSession().getAttribute("order");
        Orderitem orderitem = new Orderitem();
        for (int i = 0; i < carts.size(); i++) {
            orderitem.setProductId(carts.get(i).getPid());
            orderitem.setCount(carts.get(i).getCount());
            orderitem.setSubtotal(carts.get(i).getSubtotal());
            orderitem.setOrderId(uuid);
            orderitemMapper.insert(orderitem);
        }
        return order;
    }

    //修改支付状态
    @Override
    public void payState(String orderid){
        Orders order = ordersMapper.selectByPrimaryKey(orderid);
        order.setPayState(1);
        ordersMapper.updateByPrimaryKey(order);
    }

    @Override
    public void deleteById(HttpServletRequest request){
        ordersMapper.deleteByPrimaryKey(request.getParameter("orderid"));
    }

    @Override
    public Orders seleteOrderById(String orderid){
        return ordersMapper.selectByPrimaryKey(orderid);
    }

}
