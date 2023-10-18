package com.cwc.springbootInit.controller;


import cn.hutool.core.bean.BeanUtil;
import com.cwc.springbootInit.common.BaseResponse;
import com.cwc.springbootInit.common.ResultUtils;
import com.cwc.springbootInit.constant.JwtClaimsConstant;
import com.cwc.springbootInit.model.dto.UserLoginDTO;
import com.cwc.springbootInit.model.entity.User;
import com.cwc.springbootInit.model.vo.UserLoginVO;
import com.cwc.springbootInit.properties.JwtProperties;
import com.cwc.springbootInit.service.UserService;
import com.cwc.springbootInit.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());

        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user,userLoginVO);
        // 返回jwt令牌
        userLoginVO.setToken(token);
        return ResultUtils.success(userLoginVO);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        return ResultUtils.success(true);
    }
}
