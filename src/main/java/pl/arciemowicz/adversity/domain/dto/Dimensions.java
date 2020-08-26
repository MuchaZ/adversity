package pl.arciemowicz.adversity.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dimensions {

    private String dataSource;
    private String campaign;
}
