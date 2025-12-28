package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "订单趋势图单点数据")
public class OrderTrendVO {
    @Schema(description = "日期")
    private LocalDate date;
    @Schema(description = "时间周期")
    private String period;
    @Schema(description = "当日订单数")
    private Integer orderCount;
    // 构造方法
    public OrderTrendVO() {}

    public OrderTrendVO(String period, Integer orderCount) {
        this.period = period;
        this.orderCount = orderCount;

        // 尝试从 period 解析 date
        if (period != null && period.length() == 10 && period.contains("-")) {
            try {
                this.date = LocalDate.parse(period);
            } catch (Exception e) {
                // 如果不是标准日期格式，date 保持为 null
            }
        }
    }

}
