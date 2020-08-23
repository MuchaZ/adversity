package pl.arciemowicz.adversity.controller;

import pl.arciemowicz.adversity.domain.dto.Dimensions;
import pl.arciemowicz.adversity.domain.dto.Metrics;
import lombok.Data;

@Data
public class AnalyticsCriteria {

    private Metrics metrics;
    private Dimensions groupByDimensions;
    private Dimensions filterOnDimensions;
}
