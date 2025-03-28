package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 套餐总览
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "套餐总览")
public class SetmealOverViewVO implements Serializable {

    @ApiModelProperty(value = "已启售数量")
    private Integer sold;

    // 已停售数量
    @ApiModelProperty(value = "已停售数量")
    private Integer discontinued;
}
