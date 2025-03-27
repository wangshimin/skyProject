package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenId(String openid);

    /**
     * 插入用户数据
     * @param user
     */
    void insert(User user);

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long userId);

    /**
     * 根据条件统计用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 获取指定时间区间内的用户统计数据
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 用户统计数据列表
     */
    @MapKey("date")
    List<Map<String, Object>> getUserStatistics(LocalDateTime beginTime, LocalDateTime endTime);
}
