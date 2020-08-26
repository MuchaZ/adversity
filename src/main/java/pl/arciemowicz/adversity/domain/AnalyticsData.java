package pl.arciemowicz.adversity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsData {

    private String dataSource;
    private String campaign;
    private String date;
    private BigDecimal clicks;
    private BigDecimal impressions;
}
