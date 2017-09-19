package com.daejong.enums;

import lombok.Getter;

/**
 * Created by Daejong on 2017/9/11.
 */
@Getter
public enum ResultEnum {

    PARAM_ERROR(1, "参数不正确"),
    PRODUCT_NOT_EXISTS(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_STATUS_UPDATE_ERROR(15, "订单状态更新异常"),
    ORDER_PAY_STATUS_ERROR(16, "订单支付状态不正确"),
    ORDER_PAY_STATUS_UPDATE_ERROR(17, "订单支付状态更新异常"),
    CART_EMPTY(18, "购物车不能为空"),
    ORDER_OWNER_ERROR(19, "该订单不属于当前用户")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
