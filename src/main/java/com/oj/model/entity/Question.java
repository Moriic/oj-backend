package com.oj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * 题库表
 * @TableName question
 */
@TableName(value ="question", autoResultMap = true)
@Data
public class Question implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 题目类型
     */
    private Integer type;

    /**
     * 题目
     */
    private String question;

    /**
     * 选项
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> options;

    /**
     * 答案
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> answer;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 
     */
    private Integer score;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}