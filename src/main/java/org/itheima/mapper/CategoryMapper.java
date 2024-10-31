package org.itheima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.itheima.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert({"insert category set id=#{id},category_name=#{categoryName},category_alias=#{categoryAlias},create_user=#{createUser},create_time=#{createTime},update_time=#{updateTime}"})
    void newCategory(Category category);


    @Select("select * from category where create_user=#{userId}")
    List<Category> getCategory(Integer userId);
}
