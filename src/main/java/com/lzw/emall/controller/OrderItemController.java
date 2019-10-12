package com.lzw.emall.controller;

import com.lzw.emall.bean.extend.OrderExt;
import com.lzw.emall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping("/orders")
    public String SelectOrderitem(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("userid")==null){
            return "redirect:/tologin";
        }
        List<List<OrderExt>> lists = orderItemService.SelectOrderitem(request);
        model.addAttribute("lists",lists);
        return "orders";
    }
}
