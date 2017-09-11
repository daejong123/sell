package com.daejong.enums;

import lombok.Getter;

/**
 * 商品状态
 * Created by Daejong on 2017/9/10.
 */

@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
