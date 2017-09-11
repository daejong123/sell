package com.daejong.exception;

import com.daejong.enums.ResultEnum;

/**
 * 系统异常统一处理点
 * Created by Daejong on 2017/9/11.
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
