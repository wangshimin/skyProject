package com.sky.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("地址簿")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "地址簿id", required = false)
    private Long id;

    @ApiModelProperty(value = "用户id", required = false)
    private Long userId;

    @ApiModelProperty(value = "收货人", required = false)
    private String consignee;

    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @ApiModelProperty(value = "性别 0 女 1 男", required = true)
    private String sex;

    @ApiModelProperty(value = "省级区划编号", required = false)
    private String provinceCode;

    @ApiModelProperty(value = "省级名称", required = false)
    private String provinceName;

    @ApiModelProperty(value = "市级区划编号", required = false)
    private String cityCode;

    @ApiModelProperty(value = "市级名称", required = false)
    private String cityName;

    @ApiModelProperty(value = "区级区划编号", required = false)
    private String districtCode;

    @ApiModelProperty(value = "区级名称", required = false)
    private String districtName;

    @ApiModelProperty(value = "详细地址", required = true)
    private String detail;

    @ApiModelProperty(value = "标签", required = false)
    private String label;

    @ApiModelProperty(value = "是否默认 0否 1是", required = false)
    private Integer isDefault;
}
