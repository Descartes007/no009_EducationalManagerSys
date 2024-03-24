package com.example.controller;

import com.example.common.Result;
import com.example.entity.Homework;
import com.example.service.HomeworkService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作业信息表前端操作接口
 **/
@RestController
@RequestMapping("/homework")
public class HomeworkController {

    @Resource
    private HomeworkService homeworkService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Homework homework) {
        homeworkService.add(homework);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        homeworkService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        homeworkService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Homework homework) {
        homeworkService.updateById(homework);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Homework homework = homeworkService.selectById(id);
        return Result.success(homework);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Homework homework ) {
        List<Homework> list = homeworkService.selectAll(homework);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Homework homework,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Homework> page = homeworkService.selectPage(homework, pageNum, pageSize);
        return Result.success(page);
    }

}