package pl.arciemowicz.adversity.domain.dto;

import lombok.Builder;
import lombok.Data;
import pl.arciemowicz.adversity.domain.AnalyticsData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class AnalyticsDataDto {

    private LocalDate date;
    private Dimensions dimensions;
    private Metrics metrics;

    public static AnalyticsDataDto buildFrom(AnalyticsData analyticsData) {
        return AnalyticsDataDto.builder()
                .date(getLocalDateFrom(analyticsData.getDate()))
                .dimensions(Dimensions.builder()
                        .dataSource(analyticsData.getDataSource())
                        .campaign(analyticsData.getCampaign())
                        .build())
                .metrics(Metrics.builder()
                        .clicks(analyticsData.getClicks())
                        .impressions(analyticsData.getImpressions())
                        .build())
                .build();
    }

    private static LocalDate getLocalDateFrom(String analyticsDataDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return LocalDate.parse(analyticsDataDate, formatter);
    }

}
