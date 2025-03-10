package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "订单提交对象")
public class OrdersSubmitDTO implements Serializable {

    @ApiModelProperty(value = "地址簿id", required = true)
    private Long addressBookId;

    @ApiModelProperty(value = "付款方式", required = true)
    private int payMethod;

    @ApiModelProperty(value = "备注", required = true)
    private String remark;

    @ApiModelProperty(value = "预计送达时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;

    @ApiModelProperty(value = "配送状态  1立即送出  0选择具体时间", required = true)
    private Integer deliveryStatus;

    @ApiModelProperty(value = "餐具数量", required = true)
    private Integer tablewareNumber;

    @ApiModelProperty(value = "餐具数量状态  1按餐量提供  0选择具体数量", required = true)
    private Integer tablewareStatus;

    @ApiModelProperty(value = "打包费", required = true)
    private Integer packAmount;

    @ApiModelProperty(value = "总金额", required = true)
    private BigDecimal amount;
}
