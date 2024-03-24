package com.example.controller;

import com.example.common.Result;
import com.example.entity.Score;
import com.example.service.ScoreService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成绩信息表前端操作接口
 **/
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Score score) {
        scoreService.add(score);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        scoreService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        scoreService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Score score) {
        scoreService.updateById(score);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Score score = scoreService.selectById(id);
        return Result.success(score);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Score score ) {
        List<Score> list = scoreService.selectAll(score);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Score score,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Score> page = scoreService.selectPage(score, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/getLine")
    public Result getLine() {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();

        // 封装一下xList和yList
        List<Score> list = scoreService.selectAll(new Score());
        // 优（90分-100分）
        xList.add("优（90分-100分）");
        yList.add(list.stream().filter(x -> x.getScore() >= 90).count());

        // 良（80分-89分）
        xList.add("良（80分-89分）");
        yList.add(list.stream().filter(x -> x.getScore() >= 80 && x.getScore() < 90).count());

        // 中（70分-79分）
        xList.add("中（70分-79分）");
        yList.add(list.stream().filter(x -> x.getScore() >= 70 && x.getScore() < 80).count());

        // 及格（60分-69分）
        xList.add("及格（60分-69分）");
        yList.add(list.stream().filter(x -> x.getScore() >= 60 && x.getScore() < 70).count());

        // 不及格（<60分）
        xList.add("不及格（<60分）");
        yList.add(list.stream().filter(x -> x.getScore() < 60).count());

        resultMap.put("text", "成绩分布统计（折线图");
        resultMap.put("subtext", "统计维度：成绩段");
        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
    }

}