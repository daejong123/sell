package com.daejong.dto;

import com.daejong.dataobject.OrderDetail;
import com.daejong.enums.OrderStatusEnum;
import com.daejong.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 各层之前用来传输的对象,
 * 因为数据库中字段不能随便添加, 但是我们又要用.
 * 故创建一个dto来进行传输
 * Created by Daejong on 2017/9/11.
 */
@Data
public class OrderDTO {

    private String orderId;

    //买家名称
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家opendid
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态, 默认为新下单0
    private Integer orderState;

    //支付状态, 默认为未支付0
    private Integer payState;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

//    //一个OrderMaster的order_id可以获取到OrderDetail中的一个或者多个详情订单.
//    //但是数据库中是没有这个字段的, 我们又想关联, 为了让他不报错, 会忽略数据库对应.
//    @Transient
    private List<OrderDetail> orderDetails;
}
