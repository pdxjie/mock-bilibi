package com.pdx.mockbilli.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-04 23:34
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {

    private Long id;

    private Long userId;

    private String nick;

    private String avatar;

    private String text;

    private String gender;

    private String birth;

    private Date createTime;

    private Date updateTime;

}
