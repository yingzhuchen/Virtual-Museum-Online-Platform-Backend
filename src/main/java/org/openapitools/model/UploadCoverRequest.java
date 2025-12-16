package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * UploadCoverRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-12T10:58:47.984808100+08:00[Asia/Shanghai]")

public class UploadCoverRequest   {
    @JsonProperty("image")
    private String image;

    @JsonProperty("sceneId")
    private Integer sceneId;

    public UploadCoverRequest image(String image) {
        this.image = image;
        return this;
    }

    /**
     * base64编码格式的图片
     * @return image
     */
    @ApiModelProperty(required = true, value = "base64编码格式的图片")
    @NotNull


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UploadCoverRequest sceneId(Integer sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    /**
     * Get sceneId
     * @return sceneId
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UploadCoverRequest uploadCoverRequest = (UploadCoverRequest) o;
        return Objects.equals(this.image, uploadCoverRequest.image) &&
                Objects.equals(this.sceneId, uploadCoverRequest.sceneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, sceneId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UploadCoverRequest {\n");

        sb.append("    image: ").append(toIndentedString(image)).append("\n");
        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

