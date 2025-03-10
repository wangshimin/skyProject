package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "订单提交结果对象")
public class OrderSubmitVO implements Serializable {

    @ApiModelProperty(value = "订单id", required = true)
    private Long id;

    @ApiModelProperty(value = "订单号", required = true)
    private String orderNumber;

    @ApiModelProperty(value = "订单金额", required = true)
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "下单时间", required = true)
    private LocalDateTime orderTime;

}
