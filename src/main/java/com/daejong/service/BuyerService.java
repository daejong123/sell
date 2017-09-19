package com.daejong.service;

import com.daejong.dto.OrderDTO;

/**
 * Created by Daejong on 2017/9/19.
 */
public interface BuyerService {

    /**
     * 查询一个订单
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     */
    OrderDTO cancelOrder(String openid, String orderId);

}
