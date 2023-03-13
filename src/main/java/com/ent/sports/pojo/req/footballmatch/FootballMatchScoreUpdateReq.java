package com.ent.sports.pojo.req.footballmatch;

import com.ent.sports.pojo.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FootballMatchScoreUpdateReq extends Id implements Serializable {

    private static final long serialVersionUID = -7917646859123730709L;

    @ApiModelProperty(value = "主队分数")
    private Integer homeTeamScore;

    @ApiModelProperty("客队分数")
    private Integer visitingTeamScore;

}
