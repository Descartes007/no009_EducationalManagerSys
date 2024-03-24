package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.College;
import com.example.mapper.CollegeMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学院信息表业务处理
 **/
@Service
public class CollegeService {

    @Resource
    private CollegeMapper collegeMapper;

    /**
     * 新增
     */
    public void add(College college) {
        collegeMapper.insert(college);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        collegeMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            collegeMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(College college) {
        collegeMapper.updateById(college);
    }

    /**
     * 根据ID查询
     */
    public College selectById(Integer id) {
        return collegeMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<College> selectAll(College college) {
        return collegeMapper.selectAll(college);
    }

    /**
     * 分页查询
     */
    public PageInfo<College> selectPage(College college, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<College> list = collegeMapper.selectAll(college);
        return PageInfo.of(list);
    }

}