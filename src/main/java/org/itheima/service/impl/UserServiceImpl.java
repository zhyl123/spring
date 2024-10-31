package org.itheima.service.impl;

import org.itheima.mapper.UserMapper;
import org.itheima.pojo.Category;
import org.itheima.pojo.User;
import org.itheima.service.UserService;
import org.itheima.utils.Md5Util;
import org.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service//告诉ioc容器这个是service类，   service类主要实现业务逻辑。   并且这个实现类是接口userService的实现类
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u =userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String =   Md5Util.getMD5String(password);
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        //Map<String,Object> map = JwtUtil.parseToken(token);

        Integer id =(Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        //Map<String,Object> map = JwtUtil.parseToken(token);

        Integer id =(Integer) map.get("id");
        newPwd = Md5Util.getMD5String(newPwd);
        userMapper.updatePwd(newPwd,id);
    }

    @Override
    public boolean comfirmPwd(String oldPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id =(Integer) map.get("id");
        String pwd = userMapper.findPwd(id);
        if(Md5Util.checkPassword(oldPwd,pwd)){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean comfirmOldNewPwd(String new_pwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id =(Integer) map.get("id");
        String pwd = userMapper.findPwd(id);
        if(Md5Util.checkPassword(new_pwd,pwd)){
            return false;
        }else {
            return true;
        }

    }


}
