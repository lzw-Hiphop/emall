package com.lzw.emall.mapper;

import com.lzw.emall.bean.Product;
import com.lzw.emall.bean.ProductExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Integer productId);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer productId);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    //分页显示所有信息
    List<Product> showlist(@Param("c_id")int cid,@Param("index")int index, @Param("pageSize")int pageSize);
    //计算数据总数
    int countIndex(int cid);

}