package com.oj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.common.BaseResponse;
import com.oj.common.ResultUtils;
import com.oj.model.entity.Question;
import com.oj.service.QuestionService;
import com.oj.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;


    @PostMapping("/add")
    public BaseResponse<Boolean> addQuestion(@RequestBody Question question) {
        question.setUserId(BaseContext.getCurrentId());
        questionService.save(question);
        return ResultUtils.success(true);
    }

    @GetMapping("/{id}")
    public BaseResponse<Question> getQuestion(@PathVariable Long id) {
        return ResultUtils.success(questionService.getById(id));
    }


    @GetMapping("/page")
    public BaseResponse<IPage<Question>> getQuestionsPage(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = BaseContext.getCurrentId();
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);

        Page<Question> questionPage = new Page<>(page, pageSize);
        IPage<Question> pagedResult = questionService.page(questionPage, queryWrapper);
        return ResultUtils.success(pagedResult);
    }

    @GetMapping("/list")
    public BaseResponse<List<Question>> getQuestionList() {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Question::getUserId, BaseContext.getCurrentId())
                .orderByDesc(Question::getCreateTime);
        return ResultUtils.success(questionService.list(queryWrapper));
    }

    @PutMapping
    public BaseResponse<Boolean> updateQuestion(@RequestBody Question question) {
        questionService.updateById(question);
        return ResultUtils.success(true);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteQuestion(@PathVariable Long id) {
        questionService.removeById(id);
        return ResultUtils.success(true);
    }
}
