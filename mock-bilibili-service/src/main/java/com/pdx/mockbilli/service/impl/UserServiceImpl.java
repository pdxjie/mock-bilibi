package com.pdx.mockbilli.service.impl;

import com.mysql.cj.util.StringUtils;
import com.pdx.mockbilli.constant.UserConstant;
import com.pdx.mockbilli.entity.UserInfo;
import com.pdx.mockbilli.mapper.UserMapper;
import com.pdx.mockbilli.entity.User;
import com.pdx.mockbilli.entity.exception.ConditionException;
import com.pdx.mockbilli.service.UserService;
import com.pdx.mockbilli.utils.MD5Util;
import com.pdx.mockbilli.utils.RSAUtil;
import com.pdx.mockbilli.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-04 23:38
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空!");
        }
        User userByPhone = this.getUserByPhone(phone);
        if (userByPhone != null){
            throw new ConditionException("该手机号已经被注册");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        //原始密码
        String  rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        }catch (Exception e){
            throw new ConditionException("密码解密失败");
        }
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
        userMapper.addUser(user);
        //添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_UNKNOW);
        userInfo.setCreateTime(now);
        userMapper.addUserInfo(userInfo);
    }

    @Override
    public String login(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不能为空");
        }
        User userByPhone = this.getUserByPhone(phone);
        if (userByPhone == null){
            throw new ConditionException("当前用户不存在");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("手机号或者密码错误");
        }
        String salt = userByPhone.getSalt();
        String md5Password = MD5Util.sign(rawPassword,salt,"UTF-8");
        if (!md5Password.equals(userByPhone.getPassword())){
            throw new ConditionException("手机号或者密码错误");
        }
        String accessToken = TokenUtil.generateToken(userByPhone.getId());
        return accessToken;
    }

    /**
     * 根据手机号获取用户
     * @param phone
     * @return
     */
    public User getUserByPhone(String phone){
        User user = userMapper.getUserByPhone(phone);
        return user;
    }
}
