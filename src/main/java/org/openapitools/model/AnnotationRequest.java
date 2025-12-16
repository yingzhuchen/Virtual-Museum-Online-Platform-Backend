package org.openapitools.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import lombok.Data;
import org.openapitools.dto.AnnotationData;
import org.openapitools.dto.LinePointList;
import org.openapitools.model.AnnotationType;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AnnotationRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-15T14:16:31.978113500+08:00[Asia/Shanghai]")
@Data
public class AnnotationRequest {
    @JsonProperty("sceneId")
    private Long sceneId;

    @JsonProperty("position_x")
    private BigDecimal positionX;

    @JsonProperty("position_y")
    private BigDecimal positionY;

    @JsonProperty("position_z")
    private BigDecimal positionZ;

    @JsonProperty("radius")
    private BigDecimal radius;

    @JsonProperty("color")
    private String color;

    @JsonProperty("name")
    private String name;

    @JsonProperty("annotationData")
    private AnnotationData annotationData;


    public AnnotationRequest sceneId(Long sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    /**
     * Get sceneId
     *
     * @return sceneId
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public AnnotationRequest positionX(BigDecimal positionX) {
        this.positionX = positionX;
        return this;
    }

    /**
     * Get positionX
     *
     * @return positionX
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public BigDecimal getPositionX() {
        return positionX;
    }

    public void setPositionX(BigDecimal positionX) {
        this.positionX = positionX;
    }

    public AnnotationRequest positionY(BigDecimal positionY) {
        this.positionY = positionY;
        return this;
    }

    /**
     * Get positionY
     *
     * @return positionY
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public BigDecimal getPositionY() {
        return positionY;
    }

    public void setPositionY(BigDecimal positionY) {
        this.positionY = positionY;
    }

    public AnnotationRequest positionZ(BigDecimal positionZ) {
        this.positionZ = positionZ;
        return this;
    }

    /**
     * Get positionZ
     *
     * @return positionZ
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public BigDecimal getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(BigDecimal positionZ) {
        this.positionZ = positionZ;
    }

    public AnnotationRequest radius(BigDecimal radius) {
        this.radius = radius;
        return this;
    }

    /**
     * 标记球体半径
     *
     * @return radius
     */
    @ApiModelProperty(required = true, value = "标记球体半径")
    @NotNull

    @Valid

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public AnnotationRequest color(String color) {
        this.color = color;
        return this;
    }

    /**
     * 用字符串表示颜色，如\"#FF0000\"表示红色，需要验证字符串合法性
     *
     * @return color
     */
    @ApiModelProperty(required = true, value = "用字符串表示颜色，如\"#FF0000\"表示红色，需要验证字符串合法性")
    @NotNull


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get type
     *
     * @return type
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid




    public AnnotationRequest name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 名称
     *
     * @return name
     */
    @ApiModelProperty(required = true, value = "名称")
    @NotNull


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnnotationRequest annotationRequest = (AnnotationRequest) o;
        return Objects.equals(this.sceneId, annotationRequest.sceneId) && Objects.equals(this.positionX, annotationRequest.positionX) && Objects.equals(this.positionY, annotationRequest.positionY) && Objects.equals(this.positionZ, annotationRequest.positionZ) && Objects.equals(this.radius, annotationRequest.radius) && Objects.equals(this.color, annotationRequest.color) && Objects.equals(annotationData.getType(), annotationRequest.annotationData.getType()) && Objects.equals(this.name, annotationRequest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, positionX, positionY, positionZ, radius, color, annotationData.getType(), name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AnnotationRequest {\n");

        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("    positionX: ").append(toIndentedString(positionX)).append("\n");
        sb.append("    positionY: ").append(toIndentedString(positionY)).append("\n");
        sb.append("    positionZ: ").append(toIndentedString(positionZ)).append("\n");
        sb.append("    radius: ").append(toIndentedString(radius)).append("\n");
        sb.append("    color: ").append(toIndentedString(color)).append("\n");
        sb.append("    type: ").append(toIndentedString(annotationData.getType())).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

