package com.pdx.mockbilli.controller;

import com.pdx.mockbilli.entity.JsonResponse;
import com.pdx.mockbilli.entity.User;
import com.pdx.mockbilli.service.UserService;
import com.pdx.mockbilli.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-04 23:39
 * @Description
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    /**
     * 新建用户
     */
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success();
    }
    
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user){
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }




}
