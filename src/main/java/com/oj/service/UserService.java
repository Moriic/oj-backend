package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.model.dto.UserLoginDTO;
import com.oj.model.dto.UserTodoDTO;
import com.oj.model.dto.UserUpdateDTO;
import com.oj.model.entity.TodoTask;
import com.oj.model.entity.User;

import java.util.List;

/**
*  针对表【user(用户)】的数据库操作Service
*/
public interface UserService extends IService<User> {

    User login(UserLoginDTO userLoginDTO);

    void register(UserLoginDTO userLoginDTO);

    String canRegister();

    void resetPasword(UserLoginDTO userLoginDTO);

    User getUserProfile();

    void setUserProfile(UserUpdateDTO userUpdateDTO);

    void setUserTodoList(UserTodoDTO userTodoDTO);

    List<TodoTask> getUserTodoList();

    List<User> getStudents();

    void deleteTodoItem(Long id);

    void deleteStudent(Long id);

    void blockStudent(Long id);
}
