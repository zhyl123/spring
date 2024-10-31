package org.itheima.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil  {
    private static final String KEY = "itheima";
    public static String genToken(Map<String,Object> claims){
        return JWT.create()
                        .withClaim("claims",claims)//添加荷载
                                .withExpiresAt( new Date(System.currentTimeMillis()+1000*60*60*12))
                                        .sign(Algorithm.HMAC256(KEY));//嘉米
    }

    public static Map<String,Object> parseToken(String token){
        //定义字符串，模拟用户传递过来的token
        return JWT.require(Algorithm.HMAC256("itheima"))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();

//        DecodedJWT decodedJWT =  jwtVerifier.verify(token);//验证token，生成解析后的jwt对象
//        Map<String, Claim> claims = decodedJWT.getClaims();//调出荷载
//        System.out.println(claims.get("user"));
        //如果篡改了头部或者载荷部分失败
        //如果密钥改了验证失败
        //token过期
    }
}
