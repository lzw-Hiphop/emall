package com.lzw.emall.service.impl;

import com.lzw.emall.bean.Category;
import com.lzw.emall.bean.CategoryExample;
import com.lzw.emall.mapper.CategoryMapper;
import com.lzw.emall.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectCategory(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andCategoryIdIsNotNull();
        return categoryMapper.selectByExample(categoryExample);
    }
}
