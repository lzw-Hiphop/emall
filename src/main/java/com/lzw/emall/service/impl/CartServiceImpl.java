package com.lzw.emall.service.impl;

import com.lzw.emall.bean.Cart;
import com.lzw.emall.bean.CartExample;
import com.lzw.emall.bean.Product;
import com.lzw.emall.mapper.CartMapper;
import com.lzw.emall.mapper.ProductMapper;
import com.lzw.emall.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public void addToCart(HttpServletRequest request){
        List<Cart> carts = null;
        int count = Integer.parseInt(request.getParameter("buyNum"));//购买数量
        //是否登陆
        if(request.getSession().getAttribute("userid")!=null){//已登录
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andUserIdEqualTo((int)request.getSession().getAttribute("userid")).andPidEqualTo(Integer.parseInt(request.getParameter("pid")));
            if(cartMapper.selectByExample(cartExample).size()==0){//购物车中没有此商品
                Product product =productMapper.selectByPrimaryKey(Integer.parseInt(request.getParameter("pid")));
                Cart cart = new Cart();
                cart.setPid(product.getProductId());
                cart.setPname(product.getProductName());
                cart.setPimage(product.getProductImage());
                cart.setBaseprice(product.getProductPrice());
                cart.setCount(count);
                BigDecimal bigDecimal = new BigDecimal(count*product.getProductPrice().intValue());
                cart.setSubtotal(bigDecimal);
                cart.setUserId((int)request.getSession().getAttribute("userid"));
                cartMapper.insert(cart);
            }else {
                Cart cart = cartMapper.selectByExample(cartExample).get(0);
                cart.setCount(cart.getCount()+count);
                BigDecimal bigDecimal = new BigDecimal(cart.getCount()*cart.getBaseprice().intValue());
                cart.setSubtotal(bigDecimal);
                cartMapper.updateByPrimaryKey(cart);
            }
        }else {//未登录
            Product product =productMapper.selectByPrimaryKey(Integer.parseInt(request.getParameter("pid")));
            Cart cart = new Cart();
            cart.setPid(product.getProductId());
            cart.setPname(product.getProductName());
            cart.setPimage(product.getProductImage());
            cart.setBaseprice(product.getProductPrice());
            cart.setCount(count);
            BigDecimal bigDecimal = new BigDecimal(count*product.getProductPrice().intValue());
            cart.setSubtotal(bigDecimal);
            cart.setCartId((int)(Math.random()*1000));
            if(request.getSession().getAttribute("carts")==null){
                carts = new ArrayList<>();
                carts.add(cart);
            }else {
                carts = (List<Cart>) request.getSession().getAttribute("carts");
                int flag = 0;
                for (int i = 0; i < carts.size(); i++) {
                    if(carts.get(i).getPid()==Integer.parseInt(request.getParameter("pid"))){
                        Cart c = carts.get(i);
                        c.setCount(c.getCount()+count);
                        BigDecimal decimal = new BigDecimal(c.getCount()*c.getBaseprice().intValue());
                        c.setSubtotal(decimal);
                        carts.remove(i);
                        carts.add(c);
                        flag =1;
                    }
                }
                if(flag==0){ carts.add(cart); }
            }
            request.getSession().setAttribute("carts",carts);
        }
    }

    @Override
    public List<Cart> cart(HttpServletRequest request){
        List<Cart> carts = null;
        CartExample cartExample = new CartExample();
        if(request.getSession().getAttribute("userid")==null){
            carts = (List<Cart>)request.getSession().getAttribute("carts");
        }else {
            int userid = (int) request.getSession().getAttribute("userid");
            cartExample.createCriteria().andUserIdEqualTo(userid);
            carts = cartMapper.selectByExample(cartExample);
        }
        return carts;
    }

    @Override
    public void deleteById(HttpServletRequest request){
        //是否登陆
        if(request.getSession().getAttribute("userid")!=null) {
            cartMapper.deleteByPrimaryKey(Integer.parseInt(request.getParameter("cartid")));
        }else {
            List<Cart> carts = (List<Cart>)request.getSession().getAttribute("carts");
            for (int i=0;i<carts.size();i++) {
                Cart c = carts.get(i);
                if(c.getCartId()==Integer.parseInt(request.getParameter("cartid"))){
                    carts.remove(i);
                }
            }
            request.getSession().setAttribute("carts",carts);
        }
    }

    @Override
    public void updateById(HttpServletRequest request){
        int count = Integer.parseInt(request.getParameter("count"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        if(request.getSession().getAttribute("userid")==null){
            List<Cart> carts =(List<Cart>)request.getSession().getAttribute("carts");
            for (int i=0;i<carts.size();i++) {
                Cart c = carts.get(i);
                if(c.getCartId()==cid){
                    carts.remove(c);
                    c.setCount(count);
                    BigDecimal bigDecimal = new BigDecimal(count*c.getBaseprice().intValue());
                    c.setSubtotal(bigDecimal);
                    carts.add(c);
                    request.getSession().setAttribute("carts",carts);
                }
            }
        }else {
            Cart cart = cartMapper.selectByPrimaryKey(cid);
            cart.setCount(count);
            BigDecimal bigDecimal = new BigDecimal(count * cart.getBaseprice().intValue());
            cart.setSubtotal(bigDecimal);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
    }

    @Override
    public void deleteByUserId(HttpServletRequest request){
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo((int)request.getSession().getAttribute("userid"));
        cartMapper.deleteByExample(cartExample);
    }


}
