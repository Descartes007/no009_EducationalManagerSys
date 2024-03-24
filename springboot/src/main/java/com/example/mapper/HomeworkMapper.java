package com.example.mapper;

import com.example.entity.Homework;

import java.util.List;

/**
 * 操作homework相关数据接口
*/
public interface HomeworkMapper {

    /**
      * 新增
    */
    int insert(Homework homework);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Homework homework);

    /**
      * 根据ID查询
    */
    Homework selectById(Integer id);

    /**
      * 查询所有
    */
    List<Homework> selectAll(Homework homework);

}