package org.openapitools.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

import lombok.Data;
import org.openapitools.dto.AnnotationData;
import org.openapitools.entity.SceneAnnotation;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AnnotationResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-15T14:16:31.978113500+08:00[Asia/Shanghai]")
@Data
public class AnnotationResponse   {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("annotationId")
    private Integer annotationId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sceneId")
    private Integer sceneId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("position_x")
    private BigDecimal positionX;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("position_y")
    private BigDecimal positionY;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("position_z")
    private BigDecimal positionZ;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("radius")
    private BigDecimal radius;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("color")
    private String color;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("annotationData")
    private AnnotationData annotationData;

    public AnnotationResponse annotationId(Integer annotationId) {
        this.annotationId = annotationId;
        return this;
    }

    /**
     * ID 编号
     * @return annotationId
     */
    @ApiModelProperty(required = true, value = "ID 编号")
    @NotNull


    public Integer getannotationId() {
        return annotationId;
    }

    public void setannotationId(Integer annotationId) {
        this.annotationId = annotationId;
    }

    public AnnotationResponse sceneId(Integer sceneId) {
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

    public AnnotationResponse positionX(BigDecimal positionX) {
        this.positionX = positionX;
        return this;
    }

    /**
     * Get positionX
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

    public AnnotationResponse positionY(BigDecimal positionY) {
        this.positionY = positionY;
        return this;
    }

    /**
     * Get positionY
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

    public AnnotationResponse positionZ(BigDecimal positionZ) {
        this.positionZ = positionZ;
        return this;
    }

    /**
     * Get positionZ
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

    public AnnotationResponse radius(BigDecimal radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Get radius
     * @return radius
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public AnnotationResponse color(String color) {
        this.color = color;
        return this;
    }

    /**
     * Get color
     * @return color
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get type
     * @return type
     */

    public AnnotationResponse name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     * @return name
     */
    @ApiModelProperty(required = true, value = "")
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
        AnnotationResponse annotationResponse = (AnnotationResponse) o;
        return Objects.equals(this.annotationId, annotationResponse.annotationId) &&
                Objects.equals(this.sceneId, annotationResponse.sceneId) &&
                Objects.equals(this.positionX, annotationResponse.positionX) &&
                Objects.equals(this.positionY, annotationResponse.positionY) &&
                Objects.equals(this.positionZ, annotationResponse.positionZ) &&
                Objects.equals(this.radius, annotationResponse.radius) &&
                Objects.equals(this.color, annotationResponse.color) &&
                Objects.equals(this.name, annotationResponse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotationId, sceneId, positionX, positionY, positionZ, radius, color, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AnnotationResponse {\n");

        sb.append("    annotationId: ").append(toIndentedString(annotationId)).append("\n");
        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("    positionX: ").append(toIndentedString(positionX)).append("\n");
        sb.append("    positionY: ").append(toIndentedString(positionY)).append("\n");
        sb.append("    positionZ: ").append(toIndentedString(positionZ)).append("\n");
        sb.append("    radius: ").append(toIndentedString(radius)).append("\n");
        sb.append("    color: ").append(toIndentedString(color)).append("\n");
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

    public void setAnnotation(SceneAnnotation sceneAnnotation) {
        this.sceneId=(int)sceneAnnotation.getSceneId();
        this.annotationId=(int)sceneAnnotation.getAnnotationId();
        this.name=sceneAnnotation.getName();
        this.color=sceneAnnotation.getColor();
        this.radius= BigDecimal.valueOf(sceneAnnotation.getRadius());
        this.positionX=BigDecimal.valueOf(sceneAnnotation.getPositionX());
        this.positionY=BigDecimal.valueOf(sceneAnnotation.getPositionY());
        this.positionZ=BigDecimal.valueOf(sceneAnnotation.getPositionZ());
    }

}

