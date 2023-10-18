package com.cwc.springbootInit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwc.springbootInit.model.dto.UserLoginDTO;
import com.cwc.springbootInit.model.entity.User;

/**
*  针对表【user(用户)】的数据库操作Service
*/
public interface UserService extends IService<User> {

    User login(UserLoginDTO userLoginDTO);
}
