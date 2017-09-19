package com.daejong.controller;

import com.daejong.VO.ResultVO;
import com.daejong.converter.OrderForm2OrderDTOConverter;
import com.daejong.dto.OrderDTO;
import com.daejong.enums.ResultEnum;
import com.daejong.exception.SellException;
import com.daejong.form.OrderForm;
import com.daejong.service.BuyerService;
import com.daejong.service.OrderService;
import com.daejong.service.impl.BuyerServiceImpl;
import com.daejong.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daejong on 2017/9/15.
 */
@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping(value = "/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数不正确, orderFrom = {}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (orderDTO.getOrderDetails().isEmpty()) {
            log.error("[创建订单], 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO resultDto = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", resultDto.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @PostMapping(value = "/list")
    public ResultVO<OrderDTO> list(@RequestParam("openid") String openid,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> list = orderService.findList(openid, request);
        //("createTime": 1505146593000)    Date 转存 Long
        return ResultVOUtil.success(list.getContent());
    }

    //订单详情
    @GetMapping(value = "/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderid") String orderid) {
        // TODO: 2017/9/19  不安全做法, 因为这样所有人都可以来查询信息,
        // 所以这里要对openid进行判断是否是本人.
//        OrderDTO orderDTO = orderService.findOne(orderid);
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderid);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping(value = "/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderid") String orderid) {
        // TODO: 2017/9/20 改进, 将所有的逻辑放到service来做.
//        OrderDTO orderDTO = orderService.findOne(orderid);
//        OrderDTO cancelDTO = orderService.cancel(orderDTO);
        OrderDTO cancelDTO = buyerService.cancelOrder(openid, orderid);
        return ResultVOUtil.success(cancelDTO);
    }

}
