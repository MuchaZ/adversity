package pl.arciemowicz.adversity.controller;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AnalyticsCriteria {

    private List<String> metricsToBeAggregatedOn = new ArrayList<>();
    private List<String> groupByDimensions = new ArrayList<>();
    private Map<String, String> filterOnDimensions = new HashMap<>();

    public boolean isEmpty() {
        return getMetricsToBeAggregatedOn().isEmpty()
                && getFilterOnDimensions().isEmpty()
                && getGroupByDimensions().isEmpty();
    }
}
