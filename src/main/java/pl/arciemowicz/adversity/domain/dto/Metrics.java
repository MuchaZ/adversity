package pl.arciemowicz.adversity.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Metrics {

    private long clicks;
    private long impressions;
}
