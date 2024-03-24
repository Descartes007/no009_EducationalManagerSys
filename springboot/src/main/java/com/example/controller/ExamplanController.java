package com.example.controller;

import com.example.common.Result;
import com.example.entity.Examplan;
import com.example.service.ExamplanService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 考试安排表前端操作接口
 **/
@RestController
@RequestMapping("/examplan")
public class ExamplanController {

    @Resource
    private ExamplanService examplanService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Examplan examplan) {
        examplanService.add(examplan);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        examplanService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        examplanService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Examplan examplan) {
        examplanService.updateById(examplan);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Examplan examplan = examplanService.selectById(id);
        return Result.success(examplan);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Examplan examplan ) {
        List<Examplan> list = examplanService.selectAll(examplan);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Examplan examplan,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Examplan> page = examplanService.selectPage(examplan, pageNum, pageSize);
        return Result.success(page);
    }

}