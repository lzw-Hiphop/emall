package com.lzw.emall.service;


import com.lzw.emall.bean.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    public List<User> login(HttpServletRequest request);
    public User register(HttpServletRequest request);
    public User updateInfo(HttpServletRequest request);
}
