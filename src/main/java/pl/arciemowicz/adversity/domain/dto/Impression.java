package pl.arciemowicz.adversity.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Impression {

    private LocalDate date;
    private long impressions;
}
