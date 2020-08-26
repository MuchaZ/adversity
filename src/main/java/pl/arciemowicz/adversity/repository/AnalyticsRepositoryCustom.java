package pl.arciemowicz.adversity.repository;

import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;

import java.util.List;

public interface AnalyticsRepositoryCustom {
    List<AnalyticsData> findAllByCriteria(AnalyticsCriteria analyticsCriteria);
}
