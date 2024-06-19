package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.model.entity.Examination;
import com.oj.mapper.ExaminationMapper;
import com.oj.service.ExaminationService;
import org.springframework.stereotype.Service;

/**
* @author cwc
* @description 针对表【examination(试卷表)】的数据库操作Service实现
* @createDate 2024-06-18 23:00:35
*/
@Service
public class ExaminationServiceImpl extends ServiceImpl<ExaminationMapper, Examination>
    implements ExaminationService {

}




