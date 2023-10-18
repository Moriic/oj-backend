package com.cwc.springbootInit.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserLoginVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String account;


    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 性别
     */
    private String gender;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 科室
     */
    private String department;

    /**
     * 职称
     */
    private String job;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 照片
     */
    private String photo;

    /**
     * 简介
     */
    private String profile;

    /**
     * 用户角色：doctor/admin
     */
    private String role;

    /**
     * token
     */
    private String token;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 更新时间
     */
    private Date updateTime;
}
