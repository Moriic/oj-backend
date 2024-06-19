package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.model.entity.Exercise;
import com.oj.service.ExerciseService;
import com.oj.mapper.ExerciseMapper;
import org.springframework.stereotype.Service;

/**
* @author Muradil
* @description 针对表【exercise(实验)】的数据库操作Service实现
* @createDate 2024-06-18 17:31:11
*/
@Service
public class ExerciseServiceImpl extends ServiceImpl<ExerciseMapper, Exercise>
    implements ExerciseService{

}




