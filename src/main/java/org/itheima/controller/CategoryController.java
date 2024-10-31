package org.itheima.controller;

import org.itheima.pojo.Category;
import org.itheima.pojo.Result;
import org.itheima.service.CategoryService;
import org.itheima.service.UserService;
import org.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/newCategory")
    public Result newCategory(@RequestBody Map<String,String> category_data){
        String categoryName = category_data.get("categoryName");
        String categoryAlias = category_data.get("categoryAlias");

        Category category  = new Category();
        category.setCategoryAlias(categoryAlias);
        category.setCategoryName(categoryName);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer  userId = (Integer) map.get("id");

        category.setCreateUser(userId);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        categoryService.newCategory(category);
        return Result.success();
    }
    @GetMapping("/getCategory")
    public Result<List<Category>> getCategory(){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer  userId = (Integer) map.get("id");

        List<Category> cs  =  categoryService.getCategory(userId);

        return Result.success(cs);

    }
}
