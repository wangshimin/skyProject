package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单概览数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单概览数据")
public class OrderOverViewVO implements Serializable {

    @ApiModelProperty(value = "待接单数量")
    private Integer waitingOrders;

    @ApiModelProperty(value = "待派送数量")
    private Integer deliveredOrders;

    @ApiModelProperty(value = "已完成数量")
    private Integer completedOrders;

    @ApiModelProperty(value = "已取消数量")
    private Integer cancelledOrders;

    @ApiModelProperty(value = "全部订单")
    private Integer allOrders;
}
