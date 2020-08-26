package pl.arciemowicz.adversity.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metrics {

    private BigDecimal clicks;
    private BigDecimal impressions;
}
