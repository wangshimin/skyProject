package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "订单确认传输对象")
public class OrdersConfirmDTO implements Serializable {

    @ApiModelProperty(value = "订单id")
    private Long id;
    @ApiModelProperty(value = "订单状态 1待付款 2待接单 3 已接单 4 派送中 5 已完成 6 已取消 7 退款")
    private Integer status;

}
