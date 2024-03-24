package com.example.mapper;

import com.example.entity.Attendance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作attendance相关数据接口
*/
public interface AttendanceMapper {

    /**
      * 新增
    */
    int insert(Attendance attendance);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Attendance attendance);

    /**
      * 根据ID查询
    */
    Attendance selectById(Integer id);

    /**
      * 查询所有
    */
    List<Attendance> selectAll(Attendance attendance);

    @Select("select * from attendance where student_id = #{studentId} and course_id = #{courseId} and time = #{time}")
    Attendance selectByStudentIdAndCourseIdAndTime(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId, @Param("time") String time);
}