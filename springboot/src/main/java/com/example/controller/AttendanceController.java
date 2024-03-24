package com.example.controller;

import com.example.common.Result;
import com.example.entity.Attendance;
import com.example.service.AttendanceService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 考勤信息表前端操作接口
 **/
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Resource
    private AttendanceService attendanceService;


    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Attendance attendance) {
        attendanceService.add(attendance);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        attendanceService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        attendanceService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Attendance attendance) {
        attendanceService.updateById(attendance);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Attendance attendance = attendanceService.selectById(id);
        return Result.success(attendance);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Attendance attendance ) {
        List<Attendance> list = attendanceService.selectAll(attendance);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Attendance attendance,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Attendance> page = attendanceService.selectPage(attendance, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/getPie")
    public Result getPie() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        // 处理数据
        List<Attendance> all = attendanceService.selectAll(new Attendance());
        Map<String, List<Attendance>> collect = all.stream().collect(Collectors.groupingBy(Attendance::getStatus));
        for (String key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", collect.get(key).size());
            list.add(map);
        }

        resultMap.put("text", "考勤状态统计图（饼图）");
        resultMap.put("subtext", "统计维度：考勤状态");
        resultMap.put("name", "考勤状态");
        resultMap.put("data", list);
        return Result.success(resultMap);
    }

}