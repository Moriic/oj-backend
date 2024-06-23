package com.oj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.common.BaseResponse;
import com.oj.common.ResultUtils;
import com.oj.model.entity.ExamFinish;
import com.oj.model.entity.Examination;
import com.oj.model.entity.Question;
import com.oj.model.entity.User;
import com.oj.model.vo.ExamDetailVO;
import com.oj.model.vo.ExamFinishVO;
import com.oj.model.vo.ExaminationVO;
import com.oj.service.ExamFinishService;
import com.oj.service.ExaminationService;
import com.oj.service.QuestionService;
import com.oj.service.UserService;
import com.oj.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/exam_finish")
public class ExamFinishController {

    @Resource
    private QuestionService questionService;

    @Resource
    private ExamFinishService examFinishService;

    @Resource
    private ExaminationService examinationService;

    @Resource
    private UserService userService;

    @PostMapping
    public BaseResponse<Long> examFinish(@RequestBody ExamFinish examFinish) {
        examFinish.setUserId(BaseContext.getCurrentId());

        List<Boolean> result = new ArrayList<>();
        Long examinationId = examFinish.getExaminationId();
        Examination examination = examinationService.getById(examinationId);
        List<Question> questions = getQuestionByIds(examination.getQuestions());

        List<List<String>> userAnswer = examFinish.getAnswer();
        Integer score = 0;
        for (int i = 0; i < questions.size(); i++) {
            result.add(false);
            Question question = questions.get(i);
            List<String> answer = question.getAnswer();
            Integer type = question.getType();
            if (type == 0 || type == 2) {
                if (!userAnswer.get(i).isEmpty() && answer.get(0).equals(userAnswer.get(i).get(0))) {
                    score += question.getScore();
                    result.set(i, true);
                }
            } else if (type == 1) {
                boolean correct = true;
                if(userAnswer.get(i).size() != answer.size()) {
                    correct = false;
                }else {
                    for (String ans : answer) {
                        if (!userAnswer.get(i).contains(ans)) {
                            correct = false;
                            break;
                        }
                    }
                }
                if (correct) {
                    score += question.getScore();
                    result.set(i, true);
                }
            }
        }
        examFinish.setResult(result);
        examFinish.setScore(score);
        examFinishService.save(examFinish);
        Long id = examFinish.getId();
        return ResultUtils.success(id);
    }

    public List<Question> getQuestionByIds(List<Integer> ids) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        return questionService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    public BaseResponse<ExamFinish> getQuestion(@PathVariable Long id) {
        return ResultUtils.success(examFinishService.getById(id));
    }

    @GetMapping("/list")
    public BaseResponse<List<ExamFinishVO>> getExamFinishList() {
        QueryWrapper<ExamFinish> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExamFinish::getUserId, BaseContext.getCurrentId())
                .orderByDesc(ExamFinish::getCreateTime);
        List<ExamFinish> list = examFinishService.list(queryWrapper);

        List<ExamFinishVO> examFinishVOList = new ArrayList<>();

        for (ExamFinish examFinish : list) {
            ExamFinishVO examFinishVO = new ExamFinishVO();
            BeanUtils.copyProperties(examFinish, examFinishVO);

            ExaminationVO examinationVO = new ExaminationVO();
            Long examinationId = examFinish.getExaminationId();
            Examination examination = examinationService.getById(examinationId);
            if (examination == null) continue;
            BeanUtils.copyProperties(examination, examinationVO);
            List<Question> questionByIds = getQuestionByIds(examination.getQuestions());
            examinationVO.setQuestions(questionByIds);
            examinationVO.setQuestionNum(questionByIds.size());
            examinationVO.setExamScore(questionByIds.stream()
                    .mapToInt(Question::getScore)
                    .sum());

            examFinishVO.setExaminationVO(examinationVO);
            examFinishVOList.add(examFinishVO);
        }
        return ResultUtils.success(examFinishVOList);
    }


    @GetMapping("/studentDetail/{id}")
    public BaseResponse<List<ExamDetailVO>> getExamDetail(@PathVariable Long id){
        QueryWrapper<ExamFinish> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExamFinish::getExaminationId, id);
        List<ExamFinish> examFinishs = examFinishService.list(queryWrapper);

        List<ExamDetailVO> examDetailVOList = new ArrayList<>();
        for (ExamFinish examFinish : examFinishs) {
            ExamDetailVO examDetailVO = new ExamDetailVO();

            Long userId = examFinish.getUserId();
            User user = userService.getById(userId);
            Examination examination = examinationService.getById(id);

            examDetailVO.setTitle(examination.getTitle());
            examDetailVO.setAccount(user.getAccount());
            examDetailVO.setName(user.getName());
            examDetailVO.setScore(examFinish.getScore());

            examDetailVOList.add(examDetailVO);
        }

        return ResultUtils.success(examDetailVOList);
    }
}
