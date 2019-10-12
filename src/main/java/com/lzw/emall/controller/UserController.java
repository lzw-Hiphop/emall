package com.lzw.emall.controller;

import com.lzw.emall.bean.User;
import com.lzw.emall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //用户注册
    @RequestMapping("/register")
    public String register(HttpServletRequest request, Model model) {
        User user = userService.register(request);
        if (user == null) {
            return "redirect:/toreg?state=error";
        } else {
            model.addAttribute("username", user.getUsername());
            return "redirect:/toreg?state=success";
        }
    }

    @RequestMapping("/userInfo")
    public String userInfo(){
        return "user_info";
    }

    @RequestMapping("/updateInfo")
    public String updateInfo(HttpServletRequest request){
        User user = userService.updateInfo(request);
        request.getSession().setAttribute("address",user.getAddress());
        request.getSession().setAttribute("realname",user.getRealname());
        request.getSession().setAttribute("telephone",user.getTelephone());
        request.getSession().setAttribute("sex",user.getSex());
        return "user_info";
    }

}
