package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据统计相关接口实现类
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 统计指定时间区间内的营业额数据
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        // 创建集合，用于存放日期
        List<LocalDate> dateList = new ArrayList<>();
        // 创建集合，用于存放营业额数据
        List<Double> turnoverList = new ArrayList<>();

        // 查询每日营业额数据
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<Map<String, Object>> dailyTurnover = orderMapper.getDailyTurnover(Orders.COMPLETED, beginTime, endTime);

        // 遍历查询结果，填充日期和营业额数据
        while (!begin.isAfter(end)) {
            dateList.add(begin);
            LocalDate finalBegin = begin;
            Double turnover = dailyTurnover.stream()
                    .filter(map -> finalBegin.equals(LocalDate.parse(map.get("order_date").toString())))
                    .mapToDouble(map -> Double.parseDouble(map.get("total_amount").toString()))
                    .findFirst()
                    .orElse(0.0);
            turnoverList.add(turnover);
            begin = begin.plusDays(1);
        }

        // 封装返回结果
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 统计用户数据
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        // 创建集合，用于存放日期
        List<LocalDate> dateList = new ArrayList<>();
        // 创建集合，用于存放用户总数数据
        List<Integer> totalUserList = new ArrayList<>();
        // 创建集合，用于存放新增用户数数据
        List<Integer> newUserList = new ArrayList<>();

        // 查询指定时间区间内的用户数据
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<Map<String, Object>> userStatistics = userMapper.getUserStatistics(beginTime, endTime); // 查询指定时间区间内的用户数据

        // 将查询结果按日期分组
        Map<LocalDate, Map<String, Integer>> userDataMap = groupUserDataByDate(userStatistics);

        // 遍历日期区间，填充数据
        while (!begin.isAfter(end)) {
            dateList.add(begin);// 日期添加到日期列表中
            Map<String, Integer> userData = userDataMap.get(begin);// 根据日期获取用户数据
            if (userData != null) {
                totalUserList.add(userData.get("total_users")); // 获取总用户数
                newUserList.add(userData.get("new_users"));     // 获取新增用户数
            } else {
                totalUserList.add(0);
                newUserList.add(0);
            }
            begin = begin.plusDays(1);// 日期后移一天
        }

        // 封装返回结果
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    /**
     * 统计指定时间区间内的订单数据
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        // 创建集合，用于存放日期
        List<LocalDate> dateList = new ArrayList<>();
        // 创建集合，用于存放订单数数据
        List<Integer> orderCountList = new ArrayList<>();
        // 创建集合，用于存放有效订单数数据
        List<Integer> validOrderCountList = new ArrayList<>();

        // 查询指定时间区间内的订单数据
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<Map<String, Object>> orderStatistics = orderMapper.getOrderStatistics(beginTime, endTime);

        // 将查询结果按日期分组
        Map<LocalDate, Map<String, Integer>> orderDataMap = orderStatistics.stream()
                .collect(Collectors.toMap(
                        data -> LocalDate.parse(data.get("orderDate").toString()),
                        data -> {
                            Map<String, Integer> orderData = new HashMap<>();
                            orderData.put("orderCount", Integer.parseInt(data.get("orderCount").toString()));
                            orderData.put("validOrderCount", Integer.parseInt(data.get("validOrderCount").toString()));
                            return orderData;
                        }
                ));

        // 计算总订单数和总有效订单数
        int totalOrderCount = 0;
        int totalValidOrderCount = 0;

        // 遍历日期区间，填充数据
        while (!begin.isAfter(end)) {
            dateList.add(begin);
            Map<String, Integer> orderData = orderDataMap.get(begin);
            if (orderData != null) {
                int orderCount = orderData.get("orderCount");
                int validOrderCount = orderData.get("validOrderCount");
                orderCountList.add(orderCount);
                validOrderCountList.add(validOrderCount);
                totalOrderCount += orderCount;
                totalValidOrderCount += validOrderCount;
            } else {
                orderCountList.add(0);
                validOrderCountList.add(0);
            }
            begin = begin.plusDays(1);
        }

        // 计算订单完成率
        double orderCompletionRate = totalOrderCount == 0 ? 0 : (double) totalValidOrderCount / totalOrderCount;

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(totalValidOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 统计指定时间区间内的销量排名前10
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return
     */
    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        // 查询指定时间区间内的销量数据
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> salesData = orderMapper.getSalesTop10(beginTime, endTime);

        // 提取商品名称和销量数据
        List<String> nameList = salesData.stream()
                .map(GoodsSalesDTO::getName) // 直接获取商品名称
                .collect(Collectors.toList());
        List<Integer> numberList = salesData.stream()
                .map(GoodsSalesDTO::getNumber) // 直接获取销量
                .collect(Collectors.toList());

        // 封装返回结果
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }

    /**
     * 将用户统计数据按日期分组
     *
     * @param userStatistics 用户统计数据列表
     * @return 按日期分组的用户数据
     */
    private Map<LocalDate, Map<String, Integer>> groupUserDataByDate(List<Map<String, Object>> userStatistics) {
        return userStatistics.stream()
                .collect(Collectors.toMap(
                        data -> LocalDate.parse(data.get("date").toString()),
                        data -> {
                            Map<String, Integer> userData = new HashMap<>();
                            userData.put("total_users", Integer.parseInt(data.get("total_users").toString()));
                            userData.put("new_users", Integer.parseInt(data.get("new_users").toString()));
                            return userData;
                        }
                ));
    }
}