package com.ent.sports.pojo.req.user;

import com.ent.sports.common.constant.enums.UserStatusEnum;
import com.ent.sports.pojo.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserStatusUpdateReq extends Id implements Serializable {

    private static final long serialVersionUID = 6461153382446177771L;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty("1正常 0禁用")
    private UserStatusEnum status;

}
