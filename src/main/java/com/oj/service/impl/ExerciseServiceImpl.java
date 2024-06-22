package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.mapper.ExaminationMapper;
import com.oj.model.entity.Exercise;
import com.oj.model.vo.SearchVO;
import com.oj.service.ExerciseService;
import com.oj.mapper.ExerciseMapper;
import com.oj.utils.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private ExaminationMapper examinationMapper;

    @Override
    public List<Exercise> getSubmittedExerciseList() {
        Long currentId = BaseContext.getCurrentId();

        return exerciseMapper.getSubmittedExerciseList(currentId);
    }

    @Override
    public List<Exercise> getNoSubmittedExerciseList() {
        Long currentId = BaseContext.getCurrentId();

        return exerciseMapper.getNoSubmittedExerciseList(currentId);
    }

    @Override
    public List<Exercise> getSubmittedExerciseList(Long stuId) {
        return exerciseMapper.getSubmittedExerciseList(stuId);
    }

    @Override
    public List<Exercise> getNoSubmittedExerciseList(Long stuId) {
        return exerciseMapper.getNoSubmittedExerciseList(stuId);
    }

    @Override
    public List<SearchVO> search(String searchStr) {
        List<SearchVO> exerciseSearchRes =  exerciseMapper.search(searchStr);
        for (SearchVO exerciseSearchRe : exerciseSearchRes) {
            exerciseSearchRe.setMenu("exercise");
        }
        List<SearchVO> examSearchRes =  examinationMapper.search(searchStr);
        for (SearchVO examSearchRe : examSearchRes) {
            examSearchRe.setMenu("exam");
        }
        exerciseSearchRes.addAll(examSearchRes);
        return exerciseSearchRes;
    }
}




