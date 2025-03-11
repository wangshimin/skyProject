package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("OrdersPageQueryDTO")
public class OrdersPageQueryDTO implements Serializable {

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页条数")
    private int pageSize;

    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("下单用户手机号")
    private  String phone;

    @ApiModelProperty("订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;

    @ApiModelProperty("订单下单时间范围 开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @ApiModelProperty("订单下单时间范围 结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("用户id")
    private Long userId;

}
