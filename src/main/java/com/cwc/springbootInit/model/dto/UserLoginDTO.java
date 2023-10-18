package com.cwc.springbootInit.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {
    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;
}
