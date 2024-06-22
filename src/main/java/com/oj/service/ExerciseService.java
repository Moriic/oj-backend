package com.oj.service;

import com.oj.model.entity.Exercise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.model.vo.SearchVO;

import java.util.List;

/**
* @author Muradil
* @description 针对表【exercise(实验)】的数据库操作Service
* @createDate 2024-06-18 17:31:11
*/
public interface ExerciseService extends IService<Exercise> {

    List<Exercise> getSubmittedExerciseList();
    List<Exercise> getNoSubmittedExerciseList();
    List<Exercise> getSubmittedExerciseList(Long stuId);
    List<Exercise> getNoSubmittedExerciseList(Long stuId);

    List<SearchVO> search(String searchStr);
}
