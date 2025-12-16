package org.openapitools.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-15T14:16:31.978113500+08:00[Asia/Shanghai]")
@Data
public class AnnotationData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("type")
    private String type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("briefIntroduction")
    private String briefIntroduction;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("introduction")
    private String introduction;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("video")
    private String video;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("audioUrl")
    private String audioUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("focalPointViewX")
    private Double focalPointViewX;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("focalPointViewY")
    private Double focalPointViewY;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("focalPointViewZ")
    private Double focalPointViewZ;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("linePointList")
    private List<LinePointList> linePointList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("toSceneId")
    private Long toSceneId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("toTransmissionAnnotationId")
    private Long toTransmissionAnnotationId;

    public AnnotationData audioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        return this;
    }

    /**
     * 语音url
     * @return audioUrl
     */
    @ApiModelProperty(value = "语音url")


    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

}
