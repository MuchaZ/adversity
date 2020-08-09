package pl.arciemowicz.adversity.controller;

import pl.arciemowicz.adversity.domain.Dimensions;
import pl.arciemowicz.adversity.domain.Metrics;
import lombok.Data;

@Data
public class AnalyticsCriteria {

    private Metrics metrics;
    private Dimensions groupByDimensions;
    private Dimensions filterOnDimensions;
}
