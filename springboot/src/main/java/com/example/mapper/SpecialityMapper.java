package com.example.mapper;

import com.example.entity.Speciality;

import java.util.List;

/**
 * 操作speciality相关数据接口
*/
public interface SpecialityMapper {

    /**
      * 新增
    */
    int insert(Speciality speciality);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Speciality speciality);

    /**
      * 根据ID查询
    */
    Speciality selectById(Integer id);

    /**
      * 查询所有
    */
    List<Speciality> selectAll(Speciality speciality);

}