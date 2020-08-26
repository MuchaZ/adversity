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

    public static AnalyticsCriteria getCriteriaForRetrievingTotalClicks(AnalyticsCriteria analyticsCriteria) {
        final String clicksAggregation = "clicks";

        List<String> aggregatedOn = analyticsCriteria.getMetricsToBeAggregatedOn();
        if (!aggregatedOn.contains(clicksAggregation)) {
            aggregatedOn.add(clicksAggregation);
            analyticsCriteria.setMetricsToBeAggregatedOn(aggregatedOn);
        }

        return analyticsCriteria;
    }
}
