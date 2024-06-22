package com.oj.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实验提交
 */
@Data
public class ExerciseSubmitDTO implements Serializable {

    private Long exerciseId;

    private String content;

    private static final long serialVersionUID = 1L;
}