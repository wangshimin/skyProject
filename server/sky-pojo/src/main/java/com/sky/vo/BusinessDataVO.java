package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据概览
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "数据概览")
public class BusinessDataVO implements Serializable {

    @ApiModelProperty(value = "营业额")
    private Double turnover;

    @ApiModelProperty(value = "有效订单数")
    private Integer validOrderCount;

    @ApiModelProperty(value = "订单完成率")
    private Double orderCompletionRate;

    @ApiModelProperty(value = "平均客单价")
    private Double unitPrice;

    @ApiModelProperty(value = "新增用户数")
    private Integer newUsers;

}
