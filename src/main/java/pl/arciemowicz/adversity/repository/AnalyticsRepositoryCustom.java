package pl.arciemowicz.adversity.repository;

import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsRepositoryCustom {
    List<AnalyticsData> findAllByCriteria(AnalyticsCriteria analyticsCriteria);
    List<AnalyticsData> findAllBetweenDatesByCriteria(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria);
}
