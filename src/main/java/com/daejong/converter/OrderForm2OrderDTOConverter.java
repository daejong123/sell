package com.daejong.converter;

import com.daejong.dataobject.OrderDetail;
import com.daejong.dto.OrderDTO;
import com.daejong.enums.ResultEnum;
import com.daejong.exception.SellException;
import com.daejong.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Daejong on 2017/9/16.
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(orderForm.getOpenId());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerName(orderForm.getName());
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("[对象转换失败], string = {}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetails(orderDetailList);
        return orderDTO;
    }

}
