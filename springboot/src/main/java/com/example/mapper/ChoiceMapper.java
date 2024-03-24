package com.example.mapper;

import com.example.entity.Choice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作choice相关数据接口
*/
public interface ChoiceMapper {

    /**
      * 新增
    */
    int insert(Choice choice);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Choice choice);

    /**
      * 根据ID查询
    */
    Choice selectById(Integer id);

    /**
      * 查询所有
    */
    List<Choice> selectAll(Choice choice);

    @Select("select * from choice where course_id = #{courseId}")
    List<Choice> selectByCourseId(Integer courseId);

    @Select("select * from choice where student_id = #{studentId}")
    List<Choice> selectByStudentId(Integer studentId);

}