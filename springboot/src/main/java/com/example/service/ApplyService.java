package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Apply;
import com.example.mapper.ApplyMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 请假信息表业务处理
 **/
@Service
public class ApplyService {

    @Resource
    private ApplyMapper applyMapper;

    /**
     * 新增
     */
    public void add(Apply apply) {
        applyMapper.insert(apply);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        applyMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            applyMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Apply apply) {
        applyMapper.updateById(apply);
    }

    /**
     * 根据ID查询
     */
    public Apply selectById(Integer id) {
        return applyMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Apply> selectAll(Apply apply) {
        return applyMapper.selectAll(apply);
    }

    /**
     * 分页查询
     */
    public PageInfo<Apply> selectPage(Apply apply, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            apply.setStudentId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Apply> list = applyMapper.selectAll(apply);
        return PageInfo.of(list);
    }

}