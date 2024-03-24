package com.example.controller;

import com.example.common.Result;
import com.example.entity.Roomplan;
import com.example.service.RoomplanService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教室安排表前端操作接口
 **/
@RestController
@RequestMapping("/roomplan")
public class RoomplanController {

    @Resource
    private RoomplanService roomplanService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Roomplan roomplan) {
        roomplanService.add(roomplan);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        roomplanService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        roomplanService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Roomplan roomplan) {
        roomplanService.updateById(roomplan);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Roomplan roomplan = roomplanService.selectById(id);
        return Result.success(roomplan);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Roomplan roomplan ) {
        List<Roomplan> list = roomplanService.selectAll(roomplan);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Roomplan roomplan,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Roomplan> page = roomplanService.selectPage(roomplan, pageNum, pageSize);
        return Result.success(page);
    }

}