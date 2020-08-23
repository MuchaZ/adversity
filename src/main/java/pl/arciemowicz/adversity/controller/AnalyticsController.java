package pl.arciemowicz.adversity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.arciemowicz.adversity.domain.dto.AnalyticsDataDto;
import pl.arciemowicz.adversity.domain.dto.Impression;
import pl.arciemowicz.adversity.service.AnalyticsService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/")
    public List<AnalyticsDataDto> analyticsData(AnalyticsCriteria analyticsCriteria) {
        return analyticsService.getData(analyticsCriteria).stream().map(AnalyticsDataDto::buildFrom).collect(Collectors.toList());
    }

    @GetMapping("/totalClicks")
    public long totalClicks(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo, AnalyticsCriteria analyticsCriteria) {
        return analyticsService.getTotalClicks(dateFrom, dateTo, analyticsCriteria);
    }

    @GetMapping("/ctr")
    public long ctr(AnalyticsCriteria analyticsCriteria) {
        return analyticsService.getCtr(analyticsCriteria);
    }

    @GetMapping("/impressions")
    public List<Impression> impressions(AnalyticsCriteria analyticsCriteria) {
        return analyticsService.getImpressionsOverTime(analyticsCriteria);
    }
}
