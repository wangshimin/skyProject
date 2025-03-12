package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "订单取消传输对象")
public class OrdersCancelDTO implements Serializable {

    private Long id;
    @ApiModelProperty(value = "订单取消原因")
    private String cancelReason;

}
