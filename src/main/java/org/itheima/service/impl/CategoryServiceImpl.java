package org.itheima.service.impl;

import org.itheima.mapper.CategoryMapper;
import org.itheima.mapper.UserMapper;
import org.itheima.pojo.Category;
import org.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    public void newCategory(Category category) {
       categoryMapper.newCategory(category);
//
    }

    @Override
    public List<Category> getCategory(Integer userId) {
        return categoryMapper.getCategory(userId);

    }


}
