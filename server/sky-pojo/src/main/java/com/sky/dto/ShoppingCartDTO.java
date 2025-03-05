package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value = "ShoppingCartDTO", description = "购物车")
public class ShoppingCartDTO implements Serializable {

    @ApiModelProperty(value = "菜品id", required = false )
    private Long dishId;
    @ApiModelProperty(value = "套餐id", required = false )
    private Long setmealId;
    @ApiModelProperty(value = "口味", required = false )
    private String dishFlavor;

}
