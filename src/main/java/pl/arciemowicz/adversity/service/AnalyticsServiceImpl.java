package pl.arciemowicz.adversity.service;

import org.springframework.stereotype.Service;
import pl.arciemowicz.adversity.controller.AnalyticsCriteria;
import pl.arciemowicz.adversity.domain.AnalyticsData;
import pl.arciemowicz.adversity.domain.dto.AnalyticsDataDto;
import pl.arciemowicz.adversity.repository.AnalyticsRepository;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsServiceImpl(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public List<AnalyticsDataDto> getData(AnalyticsCriteria analyticsCriteria) {
        List<AnalyticsData> analyticsData = getAnalyticsData(analyticsCriteria);

        return analyticsData.stream().map(AnalyticsDataDto::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<AnalyticsDataDto> getTotalClicks(LocalDate dateFrom, LocalDate dateTo, AnalyticsCriteria analyticsCriteria) {
        AnalyticsCriteria.getCriteriaForRetrievingTotalClicks(analyticsCriteria);

        return analyticsRepository.findAllBetweenDatesByCriteria(dateFrom, dateTo, analyticsCriteria).stream().map(AnalyticsDataDto::buildFrom).collect(Collectors.toList());
    }

    @Override
    public List<AnalyticsDataDto> getCtr(AnalyticsCriteria analyticsCriteria) {
        List<AnalyticsData> analyticsData = getAnalyticsData(analyticsCriteria);

        return analyticsData.stream().map(data -> {
            AnalyticsDataDto analyticsDataDto = AnalyticsDataDto.buildFrom(data);
            analyticsDataDto.setCtr(data.getClicks().divide(data.getImpressions(), 2, RoundingMode.HALF_UP));
            return analyticsDataDto;
        }).collect(Collectors.toList());
    }

    private List<AnalyticsData> getAnalyticsData(AnalyticsCriteria analyticsCriteria) {
        if (analyticsCriteria.isEmpty()) {
            return analyticsRepository.findAll();
        }
        return analyticsRepository.findAllByCriteria(analyticsCriteria);
    }
}
