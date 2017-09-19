package com.daejong.service.impl;

import com.daejong.dataobject.OrderDetail;
import com.daejong.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
    private final String orderId = "1505314510249447956";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("垚");
        orderDTO.setBuyerAddress("江苏南京");
        orderDTO.setBuyerPhone("15195019957");
        orderDTO.setBuyerOpenid(openId);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123457");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetails(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.error("创建订单...", result);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO orderDTO = orderService.findOne(orderId);
        log.info("查询某个订单 result = {}", orderDTO);
        Assert.assertEquals(orderId, orderDTO.getOrderId());
    }

    @Test
    public void findList() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<OrderDTO> orderDTOPage = orderService.findList(openId, pageRequest);
        log.info("查询所有订单 result = {}", orderDTOPage);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO orderDTO1 = orderService.cancel(orderDTO);
        Assert.assertNotEquals(0, orderDTO1.getOrderState().intValue());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1505146237412180278");
        OrderDTO finish = orderService.finish(orderDTO);
        Assert.assertEquals(1, finish.getOrderState().intValue());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne("12346");
        OrderDTO paid = orderService.paid(orderDTO);
        Assert.assertEquals(1, paid.getPayState().intValue());
    }

}