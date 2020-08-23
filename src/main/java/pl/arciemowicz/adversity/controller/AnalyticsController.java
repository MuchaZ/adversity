package pl.arciemowicz.adversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.arciemowicz.adversity.domain.dto.AnalyticsDataDto;
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
    public AnalyticsDataDto totalClicks(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo, @RequestParam String dataSource, AnalyticsCriteria analyticsCriteria) {
        return null;
    }

    @GetMapping("/ctr")
    public AnalyticsDataDto ctr(AnalyticsCriteria analyticsCriteria) {
        return null;
    }

    @GetMapping("/impressions")
    public AnalyticsDataDto impressions(AnalyticsCriteria analyticsCriteria) {
        return null;
    }
}
