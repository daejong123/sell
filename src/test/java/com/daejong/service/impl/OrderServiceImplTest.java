package com.daejong.service.impl;

import com.daejong.dataobject.OrderDetail;
import com.daejong.dataobject.ProductInfo;
import com.daejong.dto.OrderDTO;
import com.daejong.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daejong on 2017/9/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String openId = "110110";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("大钟");
        orderDTO.setBuyerAddress("江苏南京");
        orderDTO.setBuyerPhone("15051816856");
        orderDTO.setBuyerOpenid(openId);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123458");
        orderDetail.setProductQuantity(10);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetails(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.error("创建订单...", result);
    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findList() throws Exception {

    }

    @Test
    public void cancel() throws Exception {

    }

    @Test
    public void finish() throws Exception {

    }

    @Test
    public void paid() throws Exception {

    }

}