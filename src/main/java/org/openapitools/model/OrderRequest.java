package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-15T14:16:31.978113500+08:00[Asia/Shanghai]")
@Data
public class OrderRequest {
    @JsonProperty("orderList")
    private List<Long> orderList;
}
