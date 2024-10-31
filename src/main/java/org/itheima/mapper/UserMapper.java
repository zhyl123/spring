package org.itheima.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.itheima.pojo.Category;
import org.itheima.pojo.User;

@Mapper//这种接口用于定义数据访问方法，通常与数据库表中的记录对应。使用 MyBatis 的 @Mapper 注解，可以将 SQL 语句与 Java 方法关联起来，从而实现数据的增删改查（CRUD）操作。
public interface UserMapper {

        //根据用户名查询用户
    @Select("select * from user where username=#{username}")//把所有东西都查出来，不对的这是
    User findByUserName(String username);

        //添加
    @Insert("insert into user(username,password,create_time,update_time)"+
    " values(#{username},#{password},now(),now())")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl} where id=#{id}")
    void updateAvatar(String avatarUrl,int id);

    @Update("update user set password=#{newPwd} where id=#{id}")
    void updatePwd(String newPwd, Integer id);

    @Select("select password from user where id=#{id}")
    String findPwd(Integer id);


}
