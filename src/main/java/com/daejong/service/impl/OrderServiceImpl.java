package com.daejong.service.impl;

import com.daejong.converter.OrderMaster2OrderDTOConverter;
import com.daejong.dataobject.OrderDetail;
import com.daejong.dataobject.OrderMaster;
import com.daejong.dataobject.ProductInfo;
import com.daejong.dto.CartDTO;
import com.daejong.dto.OrderDTO;
import com.daejong.enums.OrderStatusEnum;
import com.daejong.enums.PayStatusEnum;
import com.daejong.enums.ResultEnum;
import com.daejong.exception.SellException;
import com.daejong.repository.OrderDetailRepository;
import com.daejong.repository.OrderMasterRepository;
import com.daejong.service.OrderService;
import com.daejong.service.ProductService;
import com.daejong.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Daejong on 2017/9/11.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
//        List<CartDTO> cartDTOList = new ArrayList<>();
        //1. 查询商品(数量,单价)
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库, controller只会传几个参数过来
            // 设置随机数的主键
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }

        //3. 写入订单数据库(OrderMaster和OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderState(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayState(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4. 扣库存
//        productService.decreaseStock(cartDTOList);
        //也可以使用lambada表达式. 可以起到不污染其他代码
        List<CartDTO> cartDTOList = orderDTO.getOrderDetails()
                .stream()
                .map(e ->
                        new CartDTO(e.getProductId(), e.getProductQuantity())
                )
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList == null) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetails(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderState().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("取消订单, 订单状态不正确, orderid = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderState());
            throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
        }
        //修改订单状态
        orderDTO.setOrderState(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("取消订单, 取消订单失败, orderid = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderState());
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }
        //返还库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetails().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已经支付, 需要退款
        if (orderDTO.getPayState().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderState().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单] 订单状态不正确, orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderState());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderState(OrderStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[完结订单] 修改订单状态失败, orderId = {}, orderStatus = {}", orderMaster.getOrderId(), orderMaster.getOrderState());
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //查看订单状态
        if (!orderDTO.getOrderState().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[订单支付], 订单状态不正确, orderid = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderState());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断订单支付状态
        if (!orderDTO.getPayState().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[订单支付], 订单支付状态不正确, orderid = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderState());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改订单支付状态
        orderDTO.setPayState(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
