package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Roomplan;
import com.example.mapper.RoomplanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教室安排表业务处理
 **/
@Service
public class RoomplanService {

    @Resource
    private RoomplanMapper roomplanMapper;

    /**
     * 新增
     */
    public void add(Roomplan roomplan) {
        roomplanMapper.insert(roomplan);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        roomplanMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            roomplanMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Roomplan roomplan) {
        roomplanMapper.updateById(roomplan);
    }

    /**
     * 根据ID查询
     */
    public Roomplan selectById(Integer id) {
        return roomplanMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Roomplan> selectAll(Roomplan roomplan) {
        return roomplanMapper.selectAll(roomplan);
    }

    /**
     * 分页查询
     */
    public PageInfo<Roomplan> selectPage(Roomplan roomplan, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Roomplan> list = roomplanMapper.selectAll(roomplan);
        return PageInfo.of(list);
    }

}