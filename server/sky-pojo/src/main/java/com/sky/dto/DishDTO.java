package com.sky.dto;

import com.sky.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "菜品传输对象")
public class DishDTO implements Serializable {

    private Long id;
    @ApiModelProperty(value = "菜品名称", required = true)
    private String name;

    @ApiModelProperty(value = "菜品分类id", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "菜品价格", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "图片", required = false)
    private String image;

    @ApiModelProperty(value = "描述信息", required = false)
    private String description;

    @ApiModelProperty(value = "商品状态 0 停售 1 起售", required = false)
    private Integer status;

    @ApiModelProperty(value = "口味", required = false)
    private List<DishFlavor> flavors = new ArrayList<>();

}
