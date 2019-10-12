package com.lzw.emall.service.impl;

import com.lzw.emall.bean.*;
import com.lzw.emall.mapper.OrderitemMapper;
import com.lzw.emall.mapper.ProductMapper;
import com.lzw.emall.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrderitemMapper orderitemMapper;

    @Override
    public List<Product> selectHotProduct() {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andIsHotEqualTo(1);
        return productMapper.selectByExample(productExample);
    }

    @Override
    public List<Product> selectNewProduct() {
        ProductExample productExample = new ProductExample();
        //按数据库的某个字段排序
        String orderByClause = "puton_time DESC";
        productExample.setOrderByClause(orderByClause);
        productExample.createCriteria().andProductIdIsNotNull();
        return productMapper.selectByExample(productExample);
    }

    @Override
    public List<Product> productByCategory(HttpServletRequest request) {
        int categoryid = Integer.parseInt(request.getParameter("categoryid"));
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCategoryIdEqualTo(categoryid);
        return productMapper.selectByExample(productExample);
    }

    @Override
    public Product product_detail(HttpServletRequest request) {
        int productid = Integer.parseInt(request.getParameter("productid"));
        return productMapper.selectByPrimaryKey(productid);
    }

    @Override
    public Product selectByPrimaryKey(int pid) {
        return productMapper.selectByPrimaryKey(pid);
    }

    //减库存
    @Override
    public boolean changeStore(String orderid) {
        boolean flag = true;
        OrderitemExample orderitemExample = new OrderitemExample();
        orderitemExample.createCriteria().andOrderIdEqualTo(orderid);
        List<Orderitem> orderitems = orderitemMapper.selectByExample(orderitemExample);
        int newStore = 0;
        //检查库存是否充足，若有一样库存不够则此订单不成功
        for (int i = 0; i < orderitems.size(); i++) {
            Product product = productMapper.selectByPrimaryKey(orderitems.get(i).getProductId());
            newStore = product.getProductStore() - orderitems.get(i).getCount();
            if (newStore < 0) {
                flag = false;
                return flag;
            }
        }
        //此时库存充足，可以购买
        for (int i = 0; i < orderitems.size(); i++) {
            Product product = productMapper.selectByPrimaryKey(orderitems.get(i).getProductId());
            newStore = product.getProductStore() - orderitems.get(i).getCount();
            product.setProductStore(newStore);
            ProductExample productExample = new ProductExample();
            productExample.createCriteria().andProductIdEqualTo(orderitems.get(i).getProductId());
            productMapper.updateByExampleSelective(product, productExample);
        }
        return flag;
    }

    //查询用户列表
    @Override
    public List<Product> showlist(int cid,int index, int pageSize) {
        return productMapper.showlist(cid,index,pageSize);
    }
    //查询用户数据的总条数
    @Override
    public int countIndex(int cid) {
        return productMapper.countIndex(cid);
    }


}
