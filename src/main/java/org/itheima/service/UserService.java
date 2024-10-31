package org.itheima.service;

import org.itheima.pojo.Category;
import org.itheima.pojo.User;

public interface UserService {//这个就是登陆预注册的接口，我们可以看到，他只是定义了接口类中的      方法名 、 输入输出
    //根据用户名查询用户
    User findByUserName(String username);


    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

    boolean comfirmPwd(String oldPwd);

    boolean comfirmOldNewPwd(String new_pwd);


}
