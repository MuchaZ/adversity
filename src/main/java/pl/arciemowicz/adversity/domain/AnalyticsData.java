package pl.arciemowicz.adversity.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnalyticsData {

    private LocalDate date;
    private Dimensions dimensions;
    private Metrics metrics;

}
