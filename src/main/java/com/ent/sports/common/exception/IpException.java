package com.ent.sports.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpException extends RuntimeException{

    private Integer code = -5;

    private String message = "获取ip异常";

}
