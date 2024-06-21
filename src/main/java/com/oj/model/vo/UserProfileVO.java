package com.oj.model.vo;

import lombok.Data;

@Data
public class UserProfileVO {
    private String account;
    private String password;
    private String name;
    private String avatar;
    private String role;
    private Integer blocked;
    private String signature;
}
