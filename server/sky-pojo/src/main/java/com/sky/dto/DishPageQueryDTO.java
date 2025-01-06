package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("菜品分页查询对象")
public class DishPageQueryDTO implements Serializable {

    @ApiModelProperty(value = "页码", required = true)
    private int page;

    @ApiModelProperty(value = "每页记录数", required = true)
    private int pageSize;

    @ApiModelProperty(value = "菜品名称")
    private String name;

    @ApiModelProperty(value = "菜品分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "菜品售卖状态：0表示禁用 1表示启用 ")
    private Integer status;

}
