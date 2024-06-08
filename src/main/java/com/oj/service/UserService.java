package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.model.dto.UserLoginDTO;
import com.oj.model.entity.User;

/**
*  针对表【user(用户)】的数据库操作Service
*/
public interface UserService extends IService<User> {

    User login(UserLoginDTO userLoginDTO);
}
