package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "菜品分类数据模型")
public class CategoryDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("类型 1 菜品分类 2 套餐分类")
    private Integer type;

    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

}
