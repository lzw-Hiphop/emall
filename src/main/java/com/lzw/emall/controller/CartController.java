package com.lzw.emall.controller;

import com.lzw.emall.bean.Cart;
import com.lzw.emall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/addToCart")
    public String addToCart(HttpServletRequest request){
        cartService.addToCart(request);
        return "redirect:/cart";
    }

    @RequestMapping("/cart")
    public String cart(HttpServletRequest request, Model model){
        List<Cart> carts = cartService.cart(request);
        model.addAttribute("cart",carts);
        return "cart";
    }

    @RequestMapping("/deleteById")
    public String deleteById(HttpServletRequest request){
        cartService.deleteById(request);
        return "redirect:/cart";
    }
    @RequestMapping("/updateById")
    public String updateById(HttpServletRequest request){
        cartService.updateById(request);
        return "redirect:/cart";
    }

    @RequestMapping("/clearCart")
    public String clearCart(HttpServletRequest request){
        if(request.getSession().getAttribute("userid")==null){
            request.getSession().setAttribute("carts",null);
        }else {
            cartService.deleteByUserId(request);
        }
        return "redirect:/cart";
    }

}
