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
 * 试卷表
 * @TableName examination
 */
@TableName(value ="examination", autoResultMap = true)
@Data
public class Examination implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 试卷标题
     */
    private String title;
    /**
     * 试卷题目，以 JSON 数组形式存储
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Integer> questions;

    /**
     * 是否随机排列选择题的答案选项
     */
    private Boolean randomizeOptions;

    /**
     * 是否允许答题后查看答案
     */
    private Boolean allowViewAnswers;

    /**
     * 是否允许回退答题
     */
    private Boolean allowBackward;

    /**
     * True老师/False学生
     */
    private Boolean type;

    /**
     * 创建试卷的用户 ID
     */
    private Long userId;

    private Integer timeLimit;

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