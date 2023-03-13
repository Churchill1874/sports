package com.ent.sports.pojo.req.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserCreateReq implements Serializable {

    private static final long serialVersionUID = 7696419387300326654L;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String name;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

}
