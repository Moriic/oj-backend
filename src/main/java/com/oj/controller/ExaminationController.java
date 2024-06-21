package com.oj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.common.BaseResponse;
import com.oj.common.ResultUtils;
import com.oj.model.entity.ExamFinish;
import com.oj.model.entity.Examination;
import com.oj.model.entity.Question;
import com.oj.model.vo.ExaminationVO;
import com.oj.service.ExamFinishService;
import com.oj.service.ExaminationService;
import com.oj.service.QuestionService;
import com.oj.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/examination")
public class ExaminationController {

    @Resource
    private ExaminationService examinationService;
    @Resource
    private QuestionService questionService;
    @Resource
    private ExamFinishService examFinishService;

    @PostMapping("/add")
    public BaseResponse<Boolean> addExamination(@RequestBody Examination examination) {
        examination.setUserId(BaseContext.getCurrentId());
        examinationService.save(examination);
        return ResultUtils.success(true);
    }

    @GetMapping("/{id}")
    public BaseResponse<ExaminationVO> getExamination(@PathVariable Long id) {
        ExaminationVO examinationVO = new ExaminationVO();
        Examination examination = examinationService.getById(id);
        BeanUtils.copyProperties(examination, examinationVO);
        List<Question> questionByIds = getQuestionByIds(examination.getQuestions());
        examinationVO.setQuestions(questionByIds);
        examinationVO.setQuestionNum(questionByIds.size());
        examinationVO.setExamScore(questionByIds.stream()
                .mapToInt(Question::getScore)
                .sum());
        return ResultUtils.success(examinationVO);
    }


    @GetMapping("/page")
    public BaseResponse<IPage<Examination>> getExaminationsPage(@RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = BaseContext.getCurrentId();
        QueryWrapper<Examination> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);

        Page<Examination> examinationPage = new Page<>(page, pageSize);
        IPage<Examination> pagedResult = examinationService.page(examinationPage, queryWrapper);
        return ResultUtils.success(pagedResult);
    }

    @GetMapping("/list")
    public BaseResponse<List<ExaminationVO>> getExaminationList() {
        QueryWrapper<Examination> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Examination::getUserId, BaseContext.getCurrentId()) // userId = currentUserId
                .or()
                .eq(Examination::getType, 2)                    // type = 2
                .orderByDesc(Examination::getCreateTime);
        List<Examination> list = examinationService.list(queryWrapper);

        QueryWrapper<ExamFinish> examFinishQueryWrapper = new QueryWrapper<>();
        examFinishQueryWrapper.lambda().eq(ExamFinish::getUserId, BaseContext.getCurrentId());
        List<ExamFinish> list1 = examFinishService.list(examFinishQueryWrapper);

        // 创建 HashSet 存储 ExamFinish 中的 Examination 的 ID
        HashSet<Long> examFinishIds = new HashSet<>();
        list1.forEach(examFinish -> examFinishIds.add(examFinish.getExaminationId()));

        // 使用 Stream 过滤掉 Examination 列表中对应的 ID 在 examFinishIds 中存在的部分
        list = list.stream()
                .filter(examination -> !examFinishIds.contains(examination.getId()))
                .collect(Collectors.toList());

        List<ExaminationVO> resultList = new ArrayList<>();

        for (Examination examination : list) {
            ExaminationVO examinationVO = new ExaminationVO();
            BeanUtils.copyProperties(examination, examinationVO);
            List<Question> questionByIds = getQuestionByIds(examination.getQuestions());
            examinationVO.setQuestions(questionByIds);
            examinationVO.setQuestionNum(questionByIds.size());
            examinationVO.setExamScore(questionByIds.stream()
                    .mapToInt(Question::getScore)
                    .sum());
            resultList.add(examinationVO);
        }
        return ResultUtils.success(resultList);
    }

    @PutMapping
    public BaseResponse<Boolean> updateExamination(@RequestBody Examination examination) {
        examinationService.updateById(examination);
        return ResultUtils.success(true);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteExamination(@PathVariable Long id) {
        examinationService.removeById(id);
        return ResultUtils.success(true);
    }

    public List<Question> getQuestionByIds(List<Integer> ids) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        return questionService.list(queryWrapper);
    }

    @GetMapping("/list/teacher")
    public BaseResponse<List<ExaminationVO>> getExamListTeacher() {
        QueryWrapper<Examination> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Examination::getUserId, BaseContext.getCurrentId()) // userId = currentUserId
                .orderByDesc(Examination::getCreateTime);

        List<Examination> list = examinationService.list(queryWrapper);
        List<ExaminationVO> resultList = new ArrayList<>();
        for (Examination examination : list) {
            ExaminationVO examinationVO = new ExaminationVO();
            BeanUtils.copyProperties(examination, examinationVO);
            List<Question> questionByIds = getQuestionByIds(examination.getQuestions());
            examinationVO.setQuestions(questionByIds);
            examinationVO.setQuestionNum(questionByIds.size());
            examinationVO.setExamScore(questionByIds.stream()
                    .mapToInt(Question::getScore)
                    .sum());
            resultList.add(examinationVO);
        }
        return ResultUtils.success(resultList);
    }

    @PostMapping("/publish/{id}")
    public BaseResponse<Boolean> publisExam(@PathVariable long id) {
        Examination examination = examinationService.getById(id);
        if (examination.getType() == 1) {
            examination.setType(2);
        } else {
            examination.setType(1);
        }
        examinationService.updateById(examination);
        return ResultUtils.success(true);
    }

}
