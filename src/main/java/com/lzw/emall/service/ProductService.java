package com.lzw.emall.service;

import com.lzw.emall.bean.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {
    public List<Product> selectHotProduct();
    public List<Product> selectNewProduct();
    public List<Product> productByCategory(HttpServletRequest request);
    public Product product_detail(HttpServletRequest request);
    public Product selectByPrimaryKey(int pid);
    public boolean changeStore(String orderid);
    public List<Product> showlist(int cid,int index, int pageSize);
    public int countIndex(int cid);
}
