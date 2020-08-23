package pl.arciemowicz.adversity.domain;

import lombok.Data;

@Data
public class AnalyticsData {

    private String dataSource;
    private String campaign;
    private String date;
    private long clicks;
    private long impressions;
}
