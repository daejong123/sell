package com.daejong.dataobject;

import com.daejong.enums.OrderStatusEnum;
import com.daejong.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Daejong on 2017/9/11.
 */
@Entity
@Data
@DynamicUpdate //自动更新updateTime字段.
public class OrderMaster {

    @Id
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
    private Integer orderState = OrderStatusEnum.NEW.getCode();

    //支付状态, 默认为未支付0
    private Integer payState = PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

//    //一个OrderMaster的order_id可以获取到OrderDetail中的一个或者多个详情订单.
//    //但是数据库中是没有这个字段的, 我们又想关联, 为了让他不报错, 会忽略数据库对应.
//    //但是这样又会污染数据库, 所以我们创建一个dto来进行数据传输.
//    @Transient
//    private List<OrderDetail> orderDetails;
}
