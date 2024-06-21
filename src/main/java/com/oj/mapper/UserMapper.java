package com.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.model.entity.TodoTask;
import com.oj.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
*  针对表【user(用户)】的数据库操作Mapper
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where account = #{account}")
    User getUser(String account);

    @Insert("insert into user(account, password, role, isDelete, blocked) values( #{account}, #{password}, 'student', 0, 0)")
    boolean setUser(String account, String password);

    @Select("select config_value from system_config where config_key = 'register'")
    String getRegisterState();

    @Update("update user set password = #{password} where account = #{account}")
    boolean resetPassword(String account, String password);

    @Select("select * from user where id = #{id}")
    User getUserById(long id);

    @Update("update user set name = #{name}, signature = #{signature} where id = #{id}")
    boolean setUserMessage(long id, String name, String signature);

    @Insert("insert into todolist(task, user_id) values(#{task}, #{id})")
    boolean addTodo(String task, long id);
    @Select("select * from todolist where user_id = #{id}")
    List<TodoTask> getTodoList(long id);

    @Delete("delete from todolist where id = #{id}")
    void deleteTodoItem(Long id);

    @Delete("delete from user where id = #{id}")
    void deleteStudent(Long id);

    @Select("select blocked from user where id = #{id}")
    Integer getBlock(Long id);

    @Update("update user set blocked = #{i} where id = #{id}")
    void setBlocked(Long id, int i);
}




