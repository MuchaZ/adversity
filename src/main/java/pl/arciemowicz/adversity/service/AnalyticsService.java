package pl.arciemowicz.adversity.service;

import pl.arciemowicz.adversity.controller.AnalyticsCriteria;

import java.time.LocalDate;

public interface AnalyticsService {

    long getTotalClicks(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria);

    long getCtr(AnalyticsCriteria analyticsCriteria);

    long getImpressionsOverTime(AnalyticsCriteria analyticsCriteria);
}
