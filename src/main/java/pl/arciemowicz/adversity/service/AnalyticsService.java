package pl.arciemowicz.adversity.service;

import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsService {

    List<AnalyticsData> getData(AnalyticsCriteria analyticsCriteria);

    long getTotalClicks(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria);

    long getCtr(AnalyticsCriteria analyticsCriteria);

    long getImpressionsOverTime(AnalyticsCriteria analyticsCriteria);
}
