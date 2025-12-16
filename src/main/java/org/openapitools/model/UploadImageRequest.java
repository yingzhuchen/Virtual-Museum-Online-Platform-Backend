package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UploadImageRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-04T04:59:09.831941300+08:00[Asia/Shanghai]")
@Data
public class UploadImageRequest   {
    @JsonProperty("image")
    private String image;

    @JsonProperty("type")
    private UploadImageType type;

    @JsonProperty("sceneId")
    private Integer sceneId;

    @JsonProperty("userId")
    private Integer userId;

    public UploadImageRequest image(String image) {
        this.image = image;
        return this;
    }

    /**
     * 图片的 Base64 字符串
     * @return image
     */
    @ApiModelProperty(required = true, value = "图片的 Base64 字符串")
    @NotNull


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UploadImageRequest type(UploadImageType type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     * @return type
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public UploadImageType getType() {
        return type;
    }

    public void setType(UploadImageType type) {
        this.type = type;
    }

    public UploadImageRequest sceneId(Integer sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    /**
     * 当type为sceneCover时
     * @return sceneId
     */
    @ApiModelProperty(value = "当type为sceneCover时")


    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public UploadImageRequest userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     * 当type为avatar时
     * @return userId
     */
    @ApiModelProperty(value = "当type为avatar时")


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UploadImageRequest uploadImageRequest = (UploadImageRequest) o;
        return Objects.equals(this.image, uploadImageRequest.image) &&
                Objects.equals(this.type, uploadImageRequest.type) &&
                Objects.equals(this.sceneId, uploadImageRequest.sceneId) &&
                Objects.equals(this.userId, uploadImageRequest.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, type, sceneId, userId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UploadImageRequest {\n");

        sb.append("    image: ").append(toIndentedString(image)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

