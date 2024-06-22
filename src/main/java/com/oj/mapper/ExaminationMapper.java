package com.oj.mapper;

import com.oj.model.entity.Examination;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.model.vo.SearchVO;

import java.util.List;

/**
* @author cwc
* @description 针对表【examination(试卷表)】的数据库操作Mapper
* @createDate 2024-06-18 23:00:35
* @Entity com.oj.model.entity.Examination
*/
public interface ExaminationMapper extends BaseMapper<Examination> {

    List<SearchVO> search(String searchStr);
}




