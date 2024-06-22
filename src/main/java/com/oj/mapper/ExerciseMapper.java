package com.oj.mapper;

import com.oj.model.entity.Exercise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Muradil
* @description 针对表【exercise(实验)】的数据库操作Mapper
* @createDate 2024-06-18 17:31:11
* @Entity com.oj.model.entity.Exercise
*/
public interface ExerciseMapper extends BaseMapper<Exercise> {
    /**
     * 根据学生id查询已提交实验
     * @param id
     * @return
     */
    List<Exercise> getSubmittedExerciseList(Long id);
    /**
     * 根据学生id查询未提交实验
     * @param id
     * @return
     */
    List<Exercise> getNoSubmittedExerciseList(Long id);
}




