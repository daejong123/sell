package com.daejong.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Daejong on 2017/9/15.
 */
@Data
public class OrderForm {
    //买家姓名
    @NotEmpty(message = "姓名必填")
    private String name;

    //买家手机号
    @NotEmpty(message = "手机号必填")
    private String phone;

    //买家地址
    @NotEmpty(message = "地址必填")
    private String address;

    //买家openid很重要
    @NotEmpty(message = "openId必填")
    private String openId;

    //买家购物车
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
