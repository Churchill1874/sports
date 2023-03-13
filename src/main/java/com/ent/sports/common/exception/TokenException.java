package com.ent.sports.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenException extends RuntimeException{

    private Integer code = -2;

    private String message = "未登录";

}
