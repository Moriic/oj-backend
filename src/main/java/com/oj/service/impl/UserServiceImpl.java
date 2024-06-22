package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.common.ErrorCode;
import com.oj.exception.BusinessException;
import com.oj.exception.RegisterException;
import com.oj.exception.ResetPasswordException;
import com.oj.mapper.UserMapper;
import com.oj.model.dto.UserLoginDTO;
import com.oj.model.dto.UserTodoDTO;
import com.oj.model.dto.UserUpdateDTO;
import com.oj.model.entity.TodoTask;
import com.oj.model.entity.User;
import com.oj.model.vo.UserProfileVO;
import com.oj.service.UserService;
import com.oj.utils.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 针对表【user(用户)】的数据库操作Service实现
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    UserMapper mapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        // 获取用户信息
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();


        // 查询用户是否存在
        //User user = query().eq("account", account).one();
        User user = mapper.getUser(account);

        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        return user;
    }

    @Override
    public void register(UserLoginDTO userLoginDTO) {
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Long  count  = query().eq("account", account).count();

        if(count != 0)
            throw new RegisterException("注册账号已存在");
        boolean ans = mapper.setUser(account, password);
        if(!ans)
            throw new RegisterException("注册失败");
    }

    public String canRegister(){
        String result = mapper.getRegisterState();
        return result;
    }

    @Override
    public void resetPasword(UserLoginDTO userLoginDTO) {
        //检查账号是否存在
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Long  count  = query().eq("account", account).count();

        if(count == 0)
            throw new ResetPasswordException("账号不存在");
        //重置密码
        boolean result = mapper.resetPassword(account, password);
        if(!result)
            throw new ResetPasswordException("重置密码失败");
    }

    /**
     * 获取用户的基本信息
     * @return
     */
    @Override
    public User getUserProfile() {
        User user = mapper.getUserById(BaseContext.getCurrentId());
        return user;
    }

    @Override
    public void setUserProfile(UserUpdateDTO userUpdateDTO) {
        mapper.setUserMessage(BaseContext.getCurrentId(), userUpdateDTO.getName(), userUpdateDTO.getSignature());
    }

    @Override
    public void setUserTodoList(UserTodoDTO userTodoDTO) {
        mapper.addTodo(userTodoDTO.getTask(), BaseContext.getCurrentId());
    }

    @Override
    public List<TodoTask> getUserTodoList() {
        List<TodoTask> list = mapper.getTodoList(BaseContext.getCurrentId());
        return list;
    }

    @Override
    public List<User> getStudents() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", "student");
        return mapper.selectList(queryWrapper);
    }

    @Override
    public void deleteTodoItem(Long id) {
        mapper.deleteTodoItem(id);
    }

    @Override
    public void deleteStudent(Long id) {
        mapper.deleteStudent(id);
    }

    @Override
    public void blockStudent(Long id) {
        Integer status = mapper.getBlock(id);
        if(status == null || status != 1)
            mapper.setBlocked(id, 1);
        else
            mapper.setBlocked(id, 0);
    }
}




