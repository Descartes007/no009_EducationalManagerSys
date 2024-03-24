package com.example.mapper;

import com.example.entity.Apply;

import java.util.List;

/**
 * 操作apply相关数据接口
*/
public interface ApplyMapper {

    /**
      * 新增
    */
    int insert(Apply apply);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Apply apply);

    /**
      * 根据ID查询
    */
    Apply selectById(Integer id);

    /**
      * 查询所有
    */
    List<Apply> selectAll(Apply apply);

}