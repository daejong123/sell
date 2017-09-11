package com.daejong.enums;

import lombok.Getter;

/**
 * Created by Daejong on 2017/9/11.
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXISTS(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不足")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
