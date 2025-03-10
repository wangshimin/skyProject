package com.sky.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("订单")
public class Orders implements Serializable {

    @ApiModelProperty(value = "订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;


    @ApiModelProperty(value = "支付状态 0未支付 1已支付 2退款")
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "订单号")
    private String number;

    @ApiModelProperty(value = "订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    private Integer status;

    @ApiModelProperty(value = "下单用户id")
    private Long userId;

    @ApiModelProperty(value = "地址id")
    private Long addressBookId;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "结账时间")
    private LocalDateTime checkoutTime;

    @ApiModelProperty(value = "支付方式 1微信，2支付宝")
    private Integer payMethod;

    @ApiModelProperty(value = "支付状态 0未支付 1已支付 2退款")
    private Integer payStatus;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "下单用户名")
    private String userName;

    @ApiModelProperty(value = "下单用户手机号")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    @ApiModelProperty(value = "订单取消原因")
    private String cancelReason;

    @ApiModelProperty(value = "拒单原因")
    private String rejectionReason;

    @ApiModelProperty(value = "订单取消时间")
    private LocalDateTime cancelTime;

    @ApiModelProperty(value = "预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    @ApiModelProperty(value = "配送状态  1立即送出  0选择具体时间")
    private Integer deliveryStatus;

    @ApiModelProperty(value = "送达时间")
    private LocalDateTime deliveryTime;

    @ApiModelProperty(value = "打包费")
    private int packAmount;

    @ApiModelProperty(value = "餐具数量")
    private int tablewareNumber;

    @ApiModelProperty(value = "餐具数量状态  1按餐量提供  0选择具体数量")
    private Integer tablewareStatus;
}
