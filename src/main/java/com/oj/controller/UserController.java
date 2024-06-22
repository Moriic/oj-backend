package com.oj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.common.BaseResponse;
import com.oj.common.ResultUtils;
import com.oj.constant.JwtClaimsConstant;
import com.oj.model.dto.UserLoginDTO;
import com.oj.model.dto.UserTodoDTO;
import com.oj.model.dto.IdDTO;
import com.oj.model.dto.UserUpdateDTO;
import com.oj.model.entity.TodoTask;
import com.oj.model.entity.User;
import com.oj.model.vo.UserLoginVO;
import com.oj.model.vo.UserProfileVO;
import com.oj.properties.JwtProperties;
import com.oj.service.UserService;
import com.oj.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/student")
    public BaseResponse<List<User>> getStudent(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role","student");
        return ResultUtils.success(userService.list(queryWrapper));
    }

    /**
     * 用户注册
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody UserLoginDTO userLoginDTO) {
        userService.register(userLoginDTO);
        return ResultUtils.success(true);
    }
    @GetMapping("/canRegister")
    public BaseResponse<String> getRegisterState(){
        String result = userService.canRegister();
        return ResultUtils.success(result);
    }

    @PostMapping("/resetPassword")
    public BaseResponse<Boolean> resetPassword(@RequestBody UserLoginDTO userLoginDTO) {
        userService.resetPasword(userLoginDTO);
        return ResultUtils.success(true);
    }

    @GetMapping("/getUserMessage")
    public BaseResponse<UserProfileVO> getPersonProfile(){
        User user = userService.getUserProfile();
        UserProfileVO userProfileVO = new UserProfileVO();
        BeanUtils.copyProperties(user, userProfileVO);
        return ResultUtils.success(userProfileVO);
    }

    @PostMapping("/setUserMessage")
    public BaseResponse<Boolean> setUserMessage(@RequestBody UserUpdateDTO userUpdateDTO){
        userService.setUserProfile(userUpdateDTO);
        return ResultUtils.success(true);
    }

    /**
     * 添加代办事项
     * @return
     */
    @PostMapping("/setTodoList")
    public BaseResponse<Boolean> setTodoList(@RequestBody UserTodoDTO userTodoDTO){
        userService.setUserTodoList(userTodoDTO);
        return ResultUtils.success(true);
    }

    @GetMapping("/getTodoList")
    public BaseResponse<List<TodoTask>> getTodoList(){
        log.info("获取代办事项");
        List<TodoTask> list = userService.getUserTodoList();
        return ResultUtils.success(list);
    }

    @GetMapping("/getStudentList")
    public BaseResponse<List<User>> getStudentList(){
        return ResultUtils.success(userService.getStudents());
    }

    //删除代办事项
    @PostMapping("/deleteTask")
    public BaseResponse<Boolean> deleteTodoItem(@RequestBody IdDTO idDTO){
        userService.deleteTodoItem(idDTO.getId());
        return ResultUtils.success(true);
    }
    //删除学生
    @PostMapping("/deleteStudent")
    public BaseResponse<Boolean>  deleteStudent(@RequestBody IdDTO idDTO){
        userService.deleteStudent(idDTO.getId());
        return ResultUtils.success(true);
    }

    //禁用/开放学生账号
    @PostMapping("/blockStudent")
    public BaseResponse<Boolean>  blockStudent(@RequestBody IdDTO idDTO){
        userService.blockStudent(idDTO.getId());
        return ResultUtils.success(true);
    }
}


