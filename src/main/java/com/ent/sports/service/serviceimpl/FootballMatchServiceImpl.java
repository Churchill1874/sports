package com.ent.sports.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.sports.common.constant.enums.MatchStatusEnum;
import com.ent.sports.common.tools.TokenTools;
import com.ent.sports.entity.FootballMatch;
import com.ent.sports.mapper.FootballMatchMapper;
import com.ent.sports.pojo.req.footballmatch.FootballMatchPageReq;
import com.ent.sports.service.FootballMatchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FootballMatchServiceImpl extends ServiceImpl<FootballMatchMapper, FootballMatch> implements FootballMatchService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "footballMatch", allEntries = true)
    public boolean updateScore(Long id, Integer homeTeamScore, Integer visitingTeamScore) {
        UpdateWrapper<FootballMatch> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("home_team_score", homeTeamScore);
        updateWrapper.set("visiting_team_score", visitingTeamScore);
        updateWrapper.set("create_name", TokenTools.getToken().getName());
        updateWrapper.eq("id", id);
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "footballMatch", allEntries = true)
    public boolean add(FootballMatch po) {
        po.setScore("0-0");
        po.setStatus(MatchStatusEnum.NOT_START);
        po.setCreateName(TokenTools.getToken().getName());
        po.setCreateTime(LocalDateTime.now());
        return save(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "footballMatch", allEntries = true)
    public boolean delByIds(List<Long> idList) {
        return removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "footballMatch", allEntries = true)
    public boolean updateFootballMatch(FootballMatch po) {
        po.setScore("0-0");
        po.setStatus(MatchStatusEnum.NOT_START);
        po.setCreateName(TokenTools.getToken().getName());
        return updateById(po);
    }

    @Override
    public IPage<FootballMatch> page(FootballMatchPageReq po) {
        IPage<FootballMatch> iPage = new Page<>(po.getPageNum(), po.getPageSize());
        QueryWrapper<FootballMatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(po.getSeries()), "series", po.getSeries());
        queryWrapper.ge(po.getStartDate() != null, "start_time", po.getStartDate());
        queryWrapper.le(po.getEndDate() != null, "start_time", po.getEndDate());
        queryWrapper.eq(po.getTargetDate() != null, "start_time", po.getTargetDate());
        queryWrapper.eq(po.getStatus() != null, "status", po.getStatus());
        queryWrapper.orderByAsc("start_time");
        return page(iPage, queryWrapper);
    }

    @Override
    @Cacheable(value = "footballMatch", key = "'notStart'")
    public List<FootballMatch> getNotStart() {
        QueryWrapper<FootballMatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", MatchStatusEnum.NOT_START);
        queryWrapper.orderByAsc("start_time");
        return list(queryWrapper);
    }

    @Override
    @Cacheable(value = "footballMatch", key = "'get3DayFinished'")
    public List<FootballMatch> get3DayFinished() {
        QueryWrapper<FootballMatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("start_time", LocalDateTime.now().minusDays(3));
        queryWrapper.eq("status", MatchStatusEnum.FINISHED);
        queryWrapper.orderByAsc("start_time");
        return list(queryWrapper);
    }

    @Override
    @Cacheable(value = "footballMatch", key = "'getInProgress'")
    public List<FootballMatch> getInProgress() {
        QueryWrapper<FootballMatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", MatchStatusEnum.IN_PROGRESS);
        queryWrapper.orderByAsc("start_time");
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "footballMatch", allEntries = true)
    public boolean finished(List<Long> idList) {
        UpdateWrapper<FootballMatch> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", MatchStatusEnum.FINISHED);
        updateWrapper.in("id", idList);
        return update(updateWrapper);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "footballMatch", allEntries = true)
    public void updateInProgressStatus() {
        //获取所有未完成比赛,判断时间修改状态为进行中
        long startTime = System.currentTimeMillis();

        //获取到了比赛时间 状态还是未开始的比赛记录
        UpdateWrapper<FootballMatch> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", MatchStatusEnum.IN_PROGRESS);
        updateWrapper.le("start_time", LocalDateTime.now());
        updateWrapper.eq("status", MatchStatusEnum.NOT_START);
        update(updateWrapper);

        log.info("修改足球赛事为以进行中状态,用时{}毫秒", System.currentTimeMillis() - startTime);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "footballMatch", allEntries = true)
    public void updateFinishedStatus() {
        //超过150分钟的比赛 如果状态还是进行中 则自动修改成结束状态
        long startTime = System.currentTimeMillis();

        List<MatchStatusEnum> statusList = new ArrayList<>();
        statusList.add(MatchStatusEnum.NOT_START);
        statusList.add(MatchStatusEnum.IN_PROGRESS);

        UpdateWrapper<FootballMatch> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", MatchStatusEnum.FINISHED);
        updateWrapper.in("status", statusList);
        updateWrapper.le("start_time", LocalDateTime.now().minusMinutes(150));
        update(updateWrapper);

        log.info("修改足球赛事为以结束状态,用时{}毫秒", System.currentTimeMillis() - startTime);
    }


}
