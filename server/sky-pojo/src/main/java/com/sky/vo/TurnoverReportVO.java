package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "营业额统计结果")
public class TurnoverReportVO implements Serializable {

    @ApiModelProperty(value = "日期，以逗号分隔，例如：2022-10-01,2022-10-02,2022-10-03")
    private String dateList;

    @ApiModelProperty(value = "营业额，以逗号分隔，例如：406.0,1520.0,75.0")
    private String turnoverList;

}
