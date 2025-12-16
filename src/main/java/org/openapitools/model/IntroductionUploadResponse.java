package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-04T04:41:32.733843400+08:00[Asia/Shanghai]")
@Data
public class IntroductionUploadResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("videoUrl")
    private String videoUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("imageUrl")
    private String imageUrl;

}
