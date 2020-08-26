package pl.arciemowicz.adversity.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Impression {

    private LocalDate date;
    private BigDecimal impressions;
}
