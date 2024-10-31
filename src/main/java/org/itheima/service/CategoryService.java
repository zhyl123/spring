package org.itheima.service;

import org.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {
    void newCategory(Category category);

    List<Category> getCategory(Integer userId);
}
