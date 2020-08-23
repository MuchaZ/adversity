package pl.arciemowicz.adversity.service;

import org.springframework.stereotype.Service;
import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;
import pl.arciemowicz.adversity.repository.AnalyticsRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsServiceImpl(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public List<AnalyticsData> getData(AnalyticsCriteria analyticsCriteria) {
        return analyticsRepository.findAll();
    }

    @Override
    public long getTotalClicks(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria) {
        return 0;
    }

    @Override
    public long getCtr(AnalyticsCriteria analyticsCriteria) {
        return 0;
    }

    @Override
    public long getImpressionsOverTime(AnalyticsCriteria analyticsCriteria) {
        return 0;
    }
}
