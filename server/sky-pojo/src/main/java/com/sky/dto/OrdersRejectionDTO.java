package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "订单拒绝")
public class OrdersRejectionDTO implements Serializable {

    @ApiModelProperty(value = "订单id", required = true)
    private Long id;

    @ApiModelProperty(value = "订单拒绝原因", required = true)
    private String rejectionReason;

}
