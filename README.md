本项目约定 
- 管理端发出的请求，统一使用`/admin`作为前缀。
- 用户端发出的请求，统一使用`/user`作为前缀。

注意事项
- 当前端提交的数据和实体类中对应的属性差别比较大时，建议使用DTO来封装数据。
- DTO用来装需要的参数的对象，VO是装服务端给你返回的数据的，而entry是数据库表的所有字段属性组成的对象。封装参数可以方便校验，在参数的变量上注解，
就可以很方便的完成数据校验，并且代码的可读性很高。如果你用实体类来传参，不需要的变量是对性能的浪费，而且接口需要的参数很少并且不需要做数据校验的
时候，也可以不做封装直接传参。

## ThreadLocal
- 概念：ThreadLocal是Thread的局部变量，为每个线程提供单独一份存储空间，具有线程隔离的效果，只有在线程内才能获取到对应的值，线程外则不能访问。
- 常用方法：
    - public void set(T value) 设置当前线程的线程局部变量的值
    - public T get() 返回当前线程所对应的线程局部变量的值
    - public void remove() 移除当前线程所对应的线程局部变量

## 接口响应数据的处理 - 日期类型
- 方式一：在属性上加入注解，对日期进行格式化

```java
import java.time.LocalDateTime;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime createTime;
```
- 方式二：在 WebMvcConfiguration 中扩展Spring MVC的消息转换器，统一对日期类型进行格式化处理。
```java
/**
 * 扩展Spring MVC的消息转换器
 * @param converters
 */
@Override
protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    log.info("扩展消息转换器...");
    // 创建一个消息转换器对象
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    // 需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
    converter.setObjectMapper(new JacksonObjectMapper());
    // 将自己的消息转换器加入容器中
    converters.add(0,converter); // 索引为0，优先使用
}
```

## 公共字段填充
每次对数据进行添加修改时，都需要记录创建时间、创建人id、修改时间、修改id。
- 自定义注解AutoFill，用于标识需要进行公共字段自动填充的方法
- 自定义切面类AutoFillAspect，统一拦截加入了AutoFill注解的方法，通过反射为公共字段赋值
- 在Mapper的方法上加入AutoFill注解