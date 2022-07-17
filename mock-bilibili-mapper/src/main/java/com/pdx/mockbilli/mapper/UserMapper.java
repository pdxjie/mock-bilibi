package com.pdx.mockbilli.mapper;

import com.pdx.mockbilli.entity.User;
import com.pdx.mockbilli.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-04 23:40
 * @Description
 */
@Mapper
public interface UserMapper {
    User getUserByPhone(@Param("phone") String phone);

    Integer addUser(User user);

    Integer addUserInfo(UserInfo userInfo);
}
