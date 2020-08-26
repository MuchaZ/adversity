package pl.arciemowicz.adversity.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AnalyticsData {

    private String dataSource;
    private String campaign;
    private String date;
    private BigDecimal clicks;
    private BigDecimal impressions;
}
