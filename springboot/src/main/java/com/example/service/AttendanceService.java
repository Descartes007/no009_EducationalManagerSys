package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Course;
import com.example.entity.Attendance;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.CourseMapper;
import com.example.mapper.AttendanceMapper;
import com.example.mapper.StudentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 考勤信息表业务处理
 **/
@Service
public class AttendanceService {

    @Resource
    private AttendanceMapper attendanceMapper;

    /**
     * 新增
     */
    public void add(Attendance attendance) {
        // 判断同一个学生同一门课同一天的考勤记录只能是一条
        Attendance dbAttendance = attendanceMapper.selectByStudentIdAndCourseIdAndTime(attendance.getStudentId(), attendance.getCourseId(), attendance.getTime());
        if (ObjectUtil.isNotEmpty(dbAttendance)) {
            throw new CustomException(ResultCodeEnum.ATTENDANCE_ALREADY_ERROR);
        }
        attendanceMapper.insert(attendance);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        attendanceMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            attendanceMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Attendance attendance) {
        attendanceMapper.updateById(attendance);
    }

    /**
     * 根据ID查询
     */
    public Attendance selectById(Integer id) {
        return attendanceMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Attendance> selectAll(Attendance attendance) {
        return attendanceMapper.selectAll(attendance);
    }

    /**
     * 分页查询
     */
    public PageInfo<Attendance> selectPage(Attendance attendance, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
            attendance.setTeacherId(currentUser.getId());
        }
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            attendance.setStudentId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Attendance> list = attendanceMapper.selectAll(attendance);
        return PageInfo.of(list);
    }

}