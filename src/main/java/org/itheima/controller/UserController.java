package org.itheima.controller;
import jakarta.validation.constraints.Pattern;
import org.itheima.pojo.Category;
import org.itheima.pojo.Result;

import org.itheima.pojo.User;
import org.itheima.service.UserService;
import org.itheima.utils.JwtUtil;
import org.itheima.utils.Md5Util;
import org.itheima.utils.ThreadLocalUtil;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController//@RestController 是开发 RESTful Web 服务的核心注解，它简化了控制器的编写，自动处理请求和响应的序列化。
@RequestMapping("/user")//@RequestMapping 可以应用于整个控制器类，从而为类中的所有方法提供一个公共的基础路径，也可以应用于方法，表示该方法处理的具体请求路径。
public class UserController {
    @Autowired
    private UserService userService;//从容器里拿的，容器里整个这个类，然后写工具，比如查找用户名之类的。
                                        //当你使用 @Autowired 注入一个接口（如 UserService）时，Spring 会查找该接口的所有实现类。在这个例子中，Spring 找到了 UserServiceImpl 这个实现类。

    @PostMapping("/register")
    public Result register(String username, String password) {
        //查询用户 目的是检测改用户名是否被占用
        User u = userService.findByUserName(username);
        if (u == null) {             //没有占用
            userService.register(username, password);
            return Result.success();
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }
    @PostMapping("/login")
    public Result login(String username, String password) {
        User u = userService.findByUserName(username);
        if (u!=null){
            // 核对密码
            if ( Md5Util.checkPassword(password,u.getPassword())){

                Map<String,Object> claims = new HashMap<>();//我们通过认证信息来生成 jwt token
                claims.put("username",u.getUsername());
                claims.put("id",u.getId());
                String token =  JwtUtil.genToken(claims);//这里的jwt是个方法类。

                return Result.success(token);
            }
            else {
                return Result.error("密码错误");
            }
        }
        else {
            //提示没有注册//或者返回一个注册页面
            return Result.error("请注册");
        }
    }
    @GetMapping("/userinfo")
    public Result<User>  userinfo(){
        //根据用户名来查询用户article
        Map<String,Object> map = ThreadLocalUtil.get();
         //Map<String,Object> map = JwtUtil.parseToken(token);
         String  username = (String) map.get("username");
         User user = userService.findByUserName(username);

         return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);

        return Result.success("更新成功");
    }

    @PatchMapping("/updateAvatar")
    public Result updataAvatar(String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success("更新成功");
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        String  new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");
        String old_pwd = params.get("old_pwd");

        if(re_pwd.equals(new_pwd)){
            //判断密码是否正确
            if(userService.comfirmPwd(old_pwd)){
                if(userService.comfirmOldNewPwd(new_pwd)){
                    userService.updatePwd(new_pwd);
                    return Result.success("密码更新成功");
                }
                else{
                    return Result.error("密码不可以与原密码一样");
                }

            }
            else{
                return Result.error("密码不正确");
            }

        }else {
            System.out.printf(re_pwd+"\n");
            System.out.println(new_pwd);
            return Result.error("两次密码输入不一致比");
        }
    }


}

//java的开发流程是这样的，
// 首先，我们得到了接口文档，我们需要针对接口文档进行操作
// 其次，我们在分析每个api文档之后，我们需要设计每一个 api接口的如何实现  例如：登录 与 注册接口，  需要查找用户名是否存在  以及注册
// 开发过程中：
    // 首先，根据mvc流程，我们需要划分  controller   service   mapper  三个部分，其中
                        // controlller  用于 web访问使用，其中写一些简单的业务逻辑，简单的if之类的
                        //  service     用于 业务逻辑的详细编写
                        // mapper       用于处理数据库
        //这三个部分需要联合使用。
    //其次，我们需要对controller中需要使用的方法进行分析，对一个api接口，定义一系列java接口。
                //java接口意思是：上游controller提出了功能需求，我的java接口对器功能需求提出一系列解决方法，但是我们并不需要实现这些解决方法在接口中。
                //我们需要在实现类中重写 我们提出的java接口，并将实现类注入ioc容器中，方法类不需要注入ioc容器中。
    //然后，我们根据结构出来的java接口类，在其实现类中写清接口的具体实现代码，然后其中需要操作 数据库的 内容，写入mapper中。

    //接着，在mapper 的java接口类中定义类名 、 参数、 然后使用 mybatis来实现对数据库的操作，    @Select("select * from user where username=#{username}")//用这种注释来可以不用再写实现类了。

//然后我们就实现了一个api接口的全过程。


//关于拦截器的过程  我们使用了 spring自带的组件， 这个开发流程和 api接口不一样，
// 他需要 定义 拦截器组件 定义 拦截器内容
// 但是这个conpont 组件注释 和configrition 注释没看懂，需要看一下教程。


//线程全局变量
//存在一个线程数据取用的方法，就是我在同一个线程中，计算出一个值了，我可以把它存在线程中，然后在这个线程其他的地方可以调用，相当于是一个牛逼的全局变量。
// 并且安全，。因为 spring为每个用户都分配了一个线程，所以隔壁用户不存在偷取这个线程数据的情况