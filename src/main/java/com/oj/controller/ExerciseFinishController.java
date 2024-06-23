package com.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oj.common.BaseResponse;
import com.oj.common.ResultUtils;
import com.oj.model.dto.ExerciseSubmitDTO;
import com.oj.model.entity.Exercise;
import com.oj.model.entity.ExerciseFinish;
import com.oj.model.vo.ExamDetailVO;
import com.oj.service.ExerciseFinishService;
import com.oj.service.ExerciseService;
import com.oj.utils.BaseContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercise_finish")
public class ExerciseFinishController {

    @Resource
    private ExerciseFinishService exerciseFinishService;

    //提交实验
    @PostMapping("/submit")
    public BaseResponse<Boolean> submitExercise(@RequestBody ExerciseSubmitDTO exerciseSubmitDTO) {
        ExerciseFinish exerciseFinish = ExerciseFinish.builder()
                .userId(BaseContext.getCurrentId())
//                .userId(1L)
                .exerciseId(exerciseSubmitDTO.getExerciseId())
                .answer(exerciseSubmitDTO.getContent())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        exerciseFinishService.save(exerciseFinish);
        return ResultUtils.success(true);
    }
    //获取提交内容
    @GetMapping("/submit_content")
    public BaseResponse<String> getSubmittedContent(Long exerciseId,Long studentId) {
        studentId = studentId==null?BaseContext.getCurrentId():studentId;
        QueryWrapper<ExerciseFinish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",studentId)
                .eq("exercise_id",exerciseId);
        ExerciseFinish exerciseFinish = exerciseFinishService.getOne(queryWrapper);

        return ResultUtils.success(exerciseFinish.getAnswer());
    }
}
