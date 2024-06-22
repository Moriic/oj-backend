package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.model.entity.Exercise;
import com.oj.service.ExerciseService;
import com.oj.mapper.ExerciseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Muradil
* @description 针对表【exercise(实验)】的数据库操作Service实现
* @createDate 2024-06-18 17:31:11
*/
@Service
public class ExerciseServiceImpl extends ServiceImpl<ExerciseMapper, Exercise>
    implements ExerciseService{
    @Autowired
    private ExerciseMapper exerciseMapper;

    @Override
    public List<Exercise> getSubmittedExerciseList() {
        //todo
        return exerciseMapper.getSubmittedExerciseList(1L);
    }

    @Override
    public List<Exercise> getNoSubmittedExerciseList() {
        //todo
        return exerciseMapper.getNoSubmittedExerciseList(1L);
    }

    @Override
    public List<Exercise> getSubmittedExerciseList(Long stuId) {
        return exerciseMapper.getSubmittedExerciseList(stuId);
    }

    @Override
    public List<Exercise> getNoSubmittedExerciseList(Long stuId) {
        return exerciseMapper.getNoSubmittedExerciseList(stuId);
    }
}




