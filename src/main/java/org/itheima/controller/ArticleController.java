package org.itheima.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.itheima.pojo.Result;
import org.itheima.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController  {
    @GetMapping("/list")
    public Result<String> list(){
//        try {
//            Map<String, Object> claim = JwtUtil.parseToken(token);
//        } catch (Exception e){
//            response.setStatus(401);
//            return  Result.error("未登录");
//        }
//        //验证token

        return Result.success("所有文章数据。");
    }
}
