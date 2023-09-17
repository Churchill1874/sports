package com.ent.sports.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.sports.common.constant.UserStatusEnum;
import com.ent.sports.entity.User;
import com.ent.sports.mapper.UserMapper;
import com.ent.sports.pojo.req.user.UserPageReq;
import com.ent.sports.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(User po) {
        po.setCreateTime(LocalDateTime.now());
        po.setStatus(UserStatusEnum.NORMAL.getValue());
        return this.save(po);
    }

    @Override
    @Transactional
    public boolean updateStatus(User po) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(User::getId, po.getId())
                .set(User::getStatus, po.getStatus());
        return this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(User po) {
        return this.update(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean del(List<Long> idList) {
        return this.removeByIds(idList);
    }

    @Override
    public User getUser(Long id) {
        return this.getById(id);
    }

    @Override
    public User findByAccount(Integer account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getAccount, account);
        return this.getOne(queryWrapper);
    }

    @Override
    public User findByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getName, name);
        return this.getOne(queryWrapper);
    }

    @Override
    public IPage<User> page(UserPageReq po) {
        IPage<User> iPage = new Page<>(po.getPageNum(), po.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                //网名
                .like(StringUtils.isNotBlank(po.getName()), User::getName, po.getName())
                //账号
                .eq(po.getAccount() != null, User::getAccount, po.getAccount())
                //手机号
                .eq(StringUtils.isNotBlank(po.getPhone()), User::getPhone, po.getPhone())
                //真实姓名
                .like(StringUtils.isNotBlank(po.getRealName()), User::getRealName, po.getRealName())
                //等级
                .eq(po.getLevel() != null, User::getLevel, po.getLevel())
                //地址
                .like(StringUtils.isNotBlank(po.getAddress()), User::getAddress, po.getAddress())
                //状态
                .eq(po.getStatus() != null, User::getStatus, po.getStatus())
                //角色
                .eq(po.getRole() != null, User::getRole, po.getRole())
                //平台
                .eq(po.getPlatform() != null, User::getPlatform, po.getPlatform())
                //创建时间
                .orderByDesc(User::getCreateTime);
        return this.page(iPage, queryWrapper);
    }

    @Override
    public List<User> getList(User po) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                //网名
                .eq(StringUtils.isNotBlank(po.getName()), User::getName, po.getName())
                //账号
                .eq(po.getAccount() != null, User::getAccount, po.getAccount())
                //手机号
                .eq(StringUtils.isNotBlank(po.getPhone()), User::getPhone, po.getPhone())
                //真实姓名
                .eq(StringUtils.isNotBlank(po.getRealName()), User::getRealName, po.getRealName())
                //等级
                .eq(po.getLevel() != null, User::getLevel, po.getLevel())
                //地址
                .like(StringUtils.isNotBlank(po.getAddress()), User::getAddress, po.getAddress())
                //状态
                .eq(po.getStatus() != null, User::getStatus, po.getStatus())
                //角色
                .eq(po.getRole() != null, User::getRole, po.getRole())
                //平台
                .eq(po.getPlatform() != null, User::getPlatform, po.getPlatform())
                .orderByDesc(User::getCreateTime);
        //创建时间
        return this.list(queryWrapper);
    }

    @Override
    public List<User> findByIds(List<Long> idList) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .in(User::getId, idList)
                .orderByDesc(User::getCreateTime);
        return this.list(queryWrapper);
    }

    @Override
    public int maxAccount() {
        return userMapper.maxCount();
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getPhone, phoneNumber);
        return this.getOne(queryWrapper);
    }

}
