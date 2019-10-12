package com.lzw.emall.controller;


import com.lzw.emall.bean.User;
import com.lzw.emall.service.OrderService;
import com.lzw.emall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;


    //转发
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    //转发
    @RequestMapping("/toreg")
    public String toreg(){
        return "register";
    }

    //登陆
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        List<User> user = userService.login(request);
        String str = null;
        if(user.size()!=0){
            request.getSession().setAttribute("username",user.get(0).getUsername());
            request.getSession().setAttribute("userid",user.get(0).getUserId());
            request.getSession().setAttribute("address",user.get(0).getAddress());
            request.getSession().setAttribute("realname",user.get(0).getRealname());
            request.getSession().setAttribute("telephone",user.get(0).getTelephone());
            request.getSession().setAttribute("sex",user.get(0).getSex());
            if(request.getSession().getAttribute("carts")!=null)
            orderService.sessionToCart(request);
            str = "redirect:/home";
        }else {
            str = "redirect:/tologin?state=error";
        }
        return str;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/tologin";
    }


}
