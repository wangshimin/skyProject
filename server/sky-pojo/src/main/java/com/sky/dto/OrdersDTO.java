package com.sky.dto;

import com.sky.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(value = "订单传输对象")
public class OrdersDTO implements Serializable {

    @ApiModelProperty(value = "订单id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String number;

    @ApiModelProperty(value = "订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;

    @ApiModelProperty(value = "下单用户id")
    private Long userId;

    @ApiModelProperty(value = "地址id")
    private Long addressBookId;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "结账时间")
    private LocalDateTime checkoutTime;

    @ApiModelProperty(value = "支付方式 1微信，2支付宝")
    private Integer payMethod;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "下单用户名")
    private String userName;

    @ApiModelProperty(value = "下单用户手机号")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    private List<OrderDetail> orderDetails;

}
