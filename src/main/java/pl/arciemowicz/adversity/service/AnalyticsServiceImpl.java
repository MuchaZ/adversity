package pl.arciemowicz.adversity.service;

import org.springframework.stereotype.Service;
import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;
import pl.arciemowicz.adversity.domain.dto.Impression;
import pl.arciemowicz.adversity.repository.AnalyticsRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsServiceImpl(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public List<AnalyticsData> getData(AnalyticsCriteria analyticsCriteria) {
        if (analyticsCriteria.isEmpty()) {
            return analyticsRepository.findAll();
        }
        return analyticsRepository.findAllByCriteria(analyticsCriteria);
    }

    @Override
    public List<AnalyticsData> getTotalClicks(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria) {
        AnalyticsCriteria.getCriteriaForRetrievingTotalClicks(analyticsCriteria);
        return analyticsRepository.findAllBetweenDatesByCriteria(dateFrom, dateTo, analyticsCriteria);
    }

    @Override
    public long getCtr(AnalyticsCriteria analyticsCriteria) {
        return 0;
    }

    @Override
    public List<Impression> getImpressionsOverTime(AnalyticsCriteria analyticsCriteria) {
        return Collections.emptyList();
    }
}
