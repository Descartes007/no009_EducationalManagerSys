package com.example.mapper;

import com.example.entity.Examplan;

import java.util.List;

/**
 * 操作examplan相关数据接口
*/
public interface ExamplanMapper {

    /**
      * 新增
    */
    int insert(Examplan examplan);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Examplan examplan);

    /**
      * 根据ID查询
    */
    Examplan selectById(Integer id);

    /**
      * 查询所有
    */
    List<Examplan> selectAll(Examplan examplan);

}