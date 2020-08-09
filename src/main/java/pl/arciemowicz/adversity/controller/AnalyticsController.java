package pl.arciemowicz.adversity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.arciemowicz.adversity.domain.AnalyticsData;

@RestController("/api/analytics")
public class AnalyticsController {

    @GetMapping("/data")
    public AnalyticsData analyticsData() {
        return new AnalyticsData();
    }
}
