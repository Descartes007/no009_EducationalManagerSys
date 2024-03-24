package com.example.controller;

import com.example.common.Result;
import com.example.entity.Choice;
import com.example.entity.Curriculum;
import com.example.service.ChoiceService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 选课信息表前端操作接口
 **/
@RestController
@RequestMapping("/choice")
public class ChoiceController {

    @Resource
    private ChoiceService choiceService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Choice choice) {
        choiceService.add(choice);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        choiceService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        choiceService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Choice choice) {
        choiceService.updateById(choice);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Choice choice = choiceService.selectById(id);
        return Result.success(choice);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Choice choice ) {
        List<Choice> list = choiceService.selectAll(choice);
        return Result.success(list);
    }

    @GetMapping("/getCurriculum")
    public Result getCurriculum() {
        List<Curriculum> list = choiceService.getCurriculum();
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Choice choice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Choice> page = choiceService.selectPage(choice, pageNum, pageSize);
        return Result.success(page);
    }

}