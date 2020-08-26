package pl.arciemowicz.adversity.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.arciemowicz.adversity.domain.dto.AnalyticsDataDto;
import pl.arciemowicz.adversity.service.AnalyticsService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/")
    public List<AnalyticsDataDto> analyticsData(AnalyticsCriteria analyticsCriteria) {
        return analyticsService.getData(analyticsCriteria);
    }

    @GetMapping("/totalClicks")
    public List<AnalyticsDataDto> totalClicks(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate dateTo,
                                           AnalyticsCriteria analyticsCriteria) {
        return analyticsService.getTotalClicks(dateFrom, dateTo, analyticsCriteria);
    }

    @GetMapping("/ctr")
    public List<AnalyticsDataDto> ctr(AnalyticsCriteria analyticsCriteria) {
        return analyticsService.getCtr(analyticsCriteria);
    }
}
