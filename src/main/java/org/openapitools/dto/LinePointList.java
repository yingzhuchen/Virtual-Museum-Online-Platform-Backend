package org.openapitools.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinePointList {
    @JsonProperty("position_x")
    private double position_x;

    @JsonProperty("position_y")
    private double position_y;

    @JsonProperty("position_z")
    private double position_z;
}
