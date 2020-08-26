package pl.arciemowicz.adversity.service;

import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.dto.AnalyticsDataDto;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsService {

    List<AnalyticsDataDto> getData(AnalyticsCriteria analyticsCriteria);

    List<AnalyticsDataDto> getTotalClicks(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria);

    List<AnalyticsDataDto> getCtr(AnalyticsCriteria analyticsCriteria);
}
