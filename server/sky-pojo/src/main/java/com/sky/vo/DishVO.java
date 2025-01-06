package com.sky.vo;

import com.sky.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜品VO对象")
public class DishVO implements Serializable {

    private Long id;

    @ApiModelProperty(value = "菜品名称", required = true)
    private String name;

    @ApiModelProperty(value = "菜品分类id", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "菜品价格", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "图片", required = true)
    private String image;

    @ApiModelProperty(value = "菜品描述", required = true)
    private String description;

    @ApiModelProperty(value = "菜品状态 0 停售 1 起售", required = true)
    private Integer status;

    @ApiModelProperty(value = "更新时间", required = true)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    //菜品关联的口味
    @ApiModelProperty(value = "菜品关联的口味")
    private List<DishFlavor> flavors = new ArrayList<>();

    //private Integer copies;
}
