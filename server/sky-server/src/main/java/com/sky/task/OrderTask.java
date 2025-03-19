package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类：定时处理订单状态
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * ? ") // 每分钟执行一次
//    @Scheduled(cron = "1/5 * * * * ?")// 从第1秒开始，每5秒执行一次（！测试用！）
    public void processTimeOutOrder() {
        log.info("定时处理超时订单：{}", LocalDateTime.now());

        List<Orders> ordersList = orderMapper.getBystatusAndOrderTimeLT(Orders.PENDING_PAYMENT,
                LocalDateTime.now().plusMinutes(-15));

        if (ordersList != null && ordersList.size() > 0) {
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);// 设置订单状态为已取消
                orders.setRejectionReason("订单超时，自动取消");// 设置取消原因
                orders.setCancelTime(LocalDateTime.now());// 设置取消时间
                // 更新订单
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理一直处于派送中状态的订单
     */
    @Scheduled(cron = "0 0 1 * * ? ") // 每天凌晨1点执行一次
//    @Scheduled(cron = "0/5 * * * * ?")// 每5秒（！测试用！）
    public void processDeliveryOrder() {
        log.info("定时处理处于派送中状态的订单：{}", LocalDateTime.now());

        List<Orders> ordersList = orderMapper.getBystatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().plusHours(-1));

        if (ordersList != null && ordersList.size() > 0) {
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);// 设置订单状态为已完成
                // 更新订单
                orderMapper.update(orders);
            }
        }
    }
}
