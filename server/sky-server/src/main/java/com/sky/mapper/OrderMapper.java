package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     *
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     * @return
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    /**
     * 分页条件查询并按下单时间排序
     *
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     *
     * @param id
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(long id);

    /**
     * 根据状态统计订单数量
     *
     * @param status
     * @return
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 根据状态和下单时间查询订单
     *
     * @param status    订单状态
     * @param orderTime 下单时间
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getBystatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 获取每日营业额
     * @param status 订单状态
     * @param begin 开始时间
     * @param end 结束时间
     * @return 每日营业额数据
     */
    @MapKey("order_date")
    List<Map<String, Object>> getDailyTurnover(Integer status, LocalDateTime begin, LocalDateTime end);

    /**
     * 根据动态条件统计每日的营业额数据
     *
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 根据动态条件统计每日的订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 根据动态条件统计每日的订单数据
     * @param begin
     * @param end
     * @return
     */
    @MapKey("order_date") // 指定返回的Map的key为order_date
    List<Map<String, Object>> getOrderStatistics(LocalDateTime begin, LocalDateTime end);

    /**
     * 获取指定时间区间内销量排名前10的商品
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 商品名称和销量数据
     */
    @MapKey("name")
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);

}
