package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.service.QuestionService;
import com.oj.model.entity.Question;
import com.oj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author cwc
* @description 针对表【question(题库表)】的数据库操作Service实现
* @createDate 2024-06-16 23:58:24
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

}




