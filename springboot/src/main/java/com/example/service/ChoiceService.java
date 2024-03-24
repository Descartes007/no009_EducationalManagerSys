package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.SegmentEnum;
import com.example.common.enums.WeekEnum;
import com.example.entity.Account;
import com.example.entity.Choice;
import com.example.entity.Course;
import com.example.entity.Curriculum;
import com.example.exception.CustomException;
import com.example.mapper.ChoiceMapper;
import com.example.mapper.CourseMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 选课信息表业务处理
 **/
@Service
public class ChoiceService {

    @Resource
    private ChoiceMapper choiceMapper;
    @Resource
    private CourseMapper courseMapper;

    /**
     * 新增
     */
    public void add(Choice choice) {
        // 当前选的课
        Course course = courseMapper.selectById(choice.getCourseId());
        // 1. 先判断一下该门课选满了没有
        // 1) 我们可以在选课信息表里面查询该门课被选的次数（用这种方式）
        // 2) 这种方式大家自己去尝试：在课程信息表里面加个字段（已选人数），判断当前这门课的这个已选人数字段是否已经等于上课人数字段的值
        List<Choice> list = choiceMapper.selectByCourseId(choice.getCourseId());
        if (course.getNum().equals(list.size())) {
            throw new CustomException(ResultCodeEnum.COURSE_NUM_ERROR);
        }

        // 2. 判断该学生选的这么课的开课时间有没有和他之前选的课有冲突
        List<Choice> sList = choiceMapper.selectByStudentId(choice.getStudentId());
        for (Choice dbChoice : sList) {
            Course tmpCourse = courseMapper.selectById(dbChoice.getCourseId());
            if (course.getWeek().equals(tmpCourse.getWeek()) && course.getSegment().equals(tmpCourse.getSegment())) {
                throw new CustomException("-1", "您之前已经选过" + tmpCourse.getName() + ", 与该门课的上课时间冲突，请重新选择");
            }
        }

        choiceMapper.insert(choice);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        choiceMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            choiceMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Choice choice) {
        choiceMapper.updateById(choice);
    }

    /**
     * 根据ID查询
     */
    public Choice selectById(Integer id) {
        return choiceMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Choice> selectAll(Choice choice) {
        return choiceMapper.selectAll(choice);
    }

    /**
     * 分页查询
     */
    public PageInfo<Choice> selectPage(Choice choice, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
            choice.setTeacherId(currentUser.getId());
        }
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            choice.setStudentId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Choice> list = choiceMapper.selectAll(choice);
        return PageInfo.of(list);
    }

    /**
     * 生成对应学生的选课课表
     */
    public List<Curriculum> getCurriculum() {
        Account currentUser = TokenUtils.getCurrentUser();
        // 查询出该学生所有的选课信息
        Choice choice = new Choice();
        choice.setStudentId(currentUser.getId());
        List<Choice> choiceList = choiceMapper.selectAll(choice);

        List<Curriculum> list = new ArrayList<>();
        // 按照第几大节和周几来处理数据（总共有5大节，8列数据）
        // 第一大节（08:00 ~ 09:30）
        Curriculum first = new Curriculum();
        first.setSegment(SegmentEnum.FIRST.segment);
        processWeek(first, getWeekChoiceList(choiceList, SegmentEnum.FIRST.segment));
        list.add(first);

        // 第二大节（09:40 ~ 12:00）
        Curriculum second = new Curriculum();
        second.setSegment(SegmentEnum.SECOND.segment);
        processWeek(second, getWeekChoiceList(choiceList, SegmentEnum.SECOND.segment));
        list.add(second);

        // 第三大节（14:00 ~ 15:30）
        Curriculum third = new Curriculum();
        third.setSegment(SegmentEnum.THIRD.segment);
        processWeek(third, getWeekChoiceList(choiceList, SegmentEnum.THIRD.segment));
        list.add(third);

        // 第四大节（15:40 ~ 17:00）
        Curriculum forth = new Curriculum();
        forth.setSegment(SegmentEnum.FORTH.segment);
        processWeek(forth, getWeekChoiceList(choiceList, SegmentEnum.FORTH.segment));
        list.add(forth);

        // 第五大节（18:00 ~ 20:00）
        Curriculum fifth = new Curriculum();
        fifth.setSegment(SegmentEnum.FIFTH.segment);
        processWeek(fifth, getWeekChoiceList(choiceList, SegmentEnum.FIFTH.segment));
        list.add(fifth);

        return list;
    }

    /**
     * 处理当前第几大节的所有选课信息（周一到周日）
     */
    private List<Choice> getWeekChoiceList(List<Choice> choiceList, String segment) {
        return choiceList.stream().filter(x -> x.getSegment().equals(segment)).collect(Collectors.toList());
    }

    /**
     * 处理周一到周日的数据
     */
    private void processWeek(Curriculum curriculum, List<Choice> choiceList) {
        // 周一
        Optional<Choice> first = choiceList.stream().filter(x -> x.getWeek().equals(WeekEnum.MONDAY.week)).findFirst();
        first.ifPresent(choice -> curriculum.setMonday(choice.getName() + " (" + choice.getTeacherName() + ")"));
        // 周二
        Optional<Choice> second = choiceList.stream().filter(x -> x.getWeek().equals(WeekEnum.TUESDAY.week)).findFirst();
        second.ifPresent(choice -> curriculum.setTuesday(choice.getName() + " (" + choice.getTeacherName() + ")"));
        // 周三
        Optional<Choice> third = choiceList.stream().filter(x -> x.getWeek().equals(WeekEnum.WEDNESDAY.week)).findFirst();
        third.ifPresent(choice -> curriculum.setWednesday(choice.getName() + " (" + choice.getTeacherName() + ")"));
        // 周四
        Optional<Choice> forth = choiceList.stream().filter(x -> x.getWeek().equals(WeekEnum.THURSDAY.week)).findFirst();
        forth.ifPresent(choice -> curriculum.setThursday(choice.getName() + " (" + choice.getTeacherName() + ")"));
        // 周五
        Optional<Choice> fifth = choiceList.stream().filter(x -> x.getWeek().equals(WeekEnum.FRIDAY.week)).findFirst();
        fifth.ifPresent(choice -> curriculum.setFriday(choice.getName() + " (" + choice.getTeacherName() + ")"));
        // 周六
        Optional<Choice> sixth = choiceList.stream().filter(x -> x.getWeek().equals(WeekEnum.SATURDAY.week)).findFirst();
        sixth.ifPresent(choice -> curriculum.setSaturday(choice.getName() + " (" + choice.getTeacherName() + ")"));
        // 周日
        Optional<Choice> seventh = choiceList.stream().filter(x -> x.getWeek().equals(WeekEnum.SUNDAY.week)).findFirst();
        seventh.ifPresent(choice -> curriculum.setSunday(choice.getName() + " (" + choice.getTeacherName() + ")"));
    }
}