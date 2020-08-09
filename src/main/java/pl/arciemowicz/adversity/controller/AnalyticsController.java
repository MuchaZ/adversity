package pl.arciemowicz.adversity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.arciemowicz.adversity.domain.AnalyticsData;

import java.time.LocalDate;

@RestController("/api/analytics")
public class AnalyticsController {

    @GetMapping("/data")
    public AnalyticsData analyticsData(AnalyticsCriteria analyticsCriteria) {
        return new AnalyticsData();
    }

    @GetMapping("/data/totalClicks")
    public AnalyticsData totalClicks(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo, @RequestParam String dataSource, AnalyticsCriteria analyticsCriteria) {
        return new AnalyticsData();
    }

    @GetMapping("/data/ctr")
    public AnalyticsData ctr(AnalyticsCriteria analyticsCriteria) {
        return new AnalyticsData();
    }

    @GetMapping("/data/impressions")
    public AnalyticsData impressions(AnalyticsCriteria analyticsCriteria) {
        return new AnalyticsData();
    }
}
