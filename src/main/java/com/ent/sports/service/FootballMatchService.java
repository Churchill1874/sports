package com.ent.sports.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.sports.entity.FootballMatch;
import com.ent.sports.pojo.req.footballmatch.FootballMatchPageReq;

import java.util.List;

/**
 * 足球比赛接口
 */
public interface FootballMatchService extends IService<FootballMatch> {

    /**
     * 修改分数
     *
     * @param id
     * @param homeTeamScore
     * @param visitingTeamScore
     * @return
     */
    boolean updateScore(Long id, Integer homeTeamScore,Integer visitingTeamScore);

    /**
     * 添加赛事
     *
     * @param po
     * @return
     */
    boolean add(FootballMatch po);

    /**
     * 删除赛事
     *
     * @param idList
     * @return
     */
    boolean delByIds(List<Long> idList);

    /**
     * 修改赛事
     *
     * @param po
     * @return
     */
    boolean updateFootballMatch(FootballMatch po);

    /**
     * 分页赛事
     *
     * @param po
     * @return
     */
    IPage<FootballMatch> page(FootballMatchPageReq po);

    /**
     * 获取未开始的比赛
     *
     * @return
     */
    List<FootballMatch> getNotStart();

    /**
     * 获取3天内以结束的赛事
     *
     * @return
     */
    List<FootballMatch> get3DayFinished();

    /**
     * 获取进行中的赛事
     *
     * @return
     */
    List<FootballMatch> getInProgress();

    /**
     * 结束比赛修改状态
     *
     * @param idList
     * @return
     */
    boolean finished(List<Long> idList);

    /**
     * 修改已经开始的比赛状态
     */
    void updateInProgressStatus();

    /**
     * 修改赛事完成的状态
     */
    void updateFinishedStatus();


}
