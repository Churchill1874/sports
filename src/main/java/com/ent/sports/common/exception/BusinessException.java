package com.ent.sports.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private Integer code = -1;

    private String message = "数据异常";

    public BusinessException(String message) {
        this.message = message;
    }

}
