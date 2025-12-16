package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-23T20:50:19.184+08:00[Asia/Shanghai]")

public class SceneAnnotationIntroductionResponse {

    @JsonProperty("sceneAnnotationIntroductionId")
    private long sceneAnnotationIntroductionId;

    @JsonProperty("annotationId")
    private long annotationId;
    @JsonProperty("briefIntroduction")
    private String briefIntroduction;
    @JsonProperty("introduction")
    private String introduction;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonProperty("video")
    private String video;
    @JsonProperty("focalPointViewX")
    private double focalPointViewX; // 位置x
    @JsonProperty("focalPointViewY")
    private double focalPointViewY; // 位置y
    @JsonProperty("focalPointViewZ")
    private double focalPointViewZ; // 位置z
}
