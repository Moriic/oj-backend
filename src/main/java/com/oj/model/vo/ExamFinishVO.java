package com.oj.model.vo;

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
 * 完成表
 * @TableName exam_finish
 */
@TableName(value ="exam_finish", autoResultMap = true)
@Data
public class ExamFinishVO implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private Long examinationId;

    /**
     *
     */
    private Long userId;

    /**
     *
     */
    private Integer score;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<List<String>> answer;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Boolean> result;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private ExaminationVO examinationVO;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}