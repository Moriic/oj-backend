package com.oj.model.vo;


import lombok.Data;

@Data
public class ExamDetailVO {

    /**
     * 试卷标题
     */
    private String title;

    /**
     * 账号
     */
    private String account;
    /**
     * 姓名
     */
    private String name;

    /**
     *
     */
    private Integer score;
}
