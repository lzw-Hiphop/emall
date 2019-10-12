package com.lzw.emall.service.impl;


import com.lzw.emall.bean.User;
import com.lzw.emall.bean.UserExample;
import com.lzw.emall.mapper.UserMapper;
import com.lzw.emall.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper usermapper;

    //登陆
    @Override
    public List<User> login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<User> users = usermapper.selectByExample(example);
        return users;
    }

    //注册
    @Override
    public User register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = usermapper.selectByExample(example);

        if (users.size() != 0) {
            return null;
        } else {
            User newuser = new User();
            newuser.setUsername(username);
            newuser.setPassword(password);
            usermapper.insertSelective(newuser);
            return newuser;
        }
    }

    @Override
    public User updateInfo(HttpServletRequest request){
        String realname = request.getParameter("realname");
        String sex = request.getParameter("sex");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        int userid = (int)request.getSession().getAttribute("userid");
        User user = new User();
        user.setUserId(userid);
        user.setRealname(realname);
        user.setAddress(address);
        user.setSex(sex);
        user.setTelephone(telephone);
        usermapper.updateByPrimaryKeySelective(user);
        return user;
    }

}
