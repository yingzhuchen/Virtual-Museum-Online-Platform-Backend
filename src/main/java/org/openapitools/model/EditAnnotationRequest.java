package org.openapitools.model;

import java.util.List;
import java.util.Objects;
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
 * EditAnnotationRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-17T01:44:59.901991600+08:00[Asia/Shanghai]")
@Data
public class EditAnnotationRequest   {
    @JsonProperty("annotationId")
    private long annotationId;

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

    public EditAnnotationRequest annotationId(long annotationId) {
        this.annotationId = annotationId;
        return this;
    }

    /**
     * Get annotationId
     * @return annotationId
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public long getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(long annotationId) {
        this.annotationId = annotationId;
    }

    public EditAnnotationRequest positionX(BigDecimal positionX) {
        this.positionX = positionX;
        return this;
    }

    /**
     * Get positionX
     * @return positionX
     */
    @ApiModelProperty(value = "")

    @Valid

    public BigDecimal getPositionX() {
        return positionX;
    }

    public void setPositionX(BigDecimal positionX) {
        this.positionX = positionX;
    }

    public EditAnnotationRequest positionY(BigDecimal positionY) {
        this.positionY = positionY;
        return this;
    }

    /**
     * Get positionY
     * @return positionY
     */
    @ApiModelProperty(value = "")

    @Valid

    public BigDecimal getPositionY() {
        return positionY;
    }

    public void setPositionY(BigDecimal positionY) {
        this.positionY = positionY;
    }

    public EditAnnotationRequest positionZ(BigDecimal positionZ) {
        this.positionZ = positionZ;
        return this;
    }

    /**
     * Get positionZ
     * @return positionZ
     */
    @ApiModelProperty(value = "")

    @Valid

    public BigDecimal getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(BigDecimal positionZ) {
        this.positionZ = positionZ;
    }

    public EditAnnotationRequest radius(BigDecimal radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Get radius
     * @return radius
     */
    @ApiModelProperty(value = "")

    @Valid

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public EditAnnotationRequest color(String color) {
        this.color = color;
        return this;
    }

    /**
     * 用字符串表示颜色，如\"#FF0000\"表示红色，需要验证字符串合法性
     * @return color
     */
    @ApiModelProperty(value = "用字符串表示颜色，如\"#FF0000\"表示红色，需要验证字符串合法性")


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
    @ApiModelProperty(value = "")

    @Valid



    public EditAnnotationRequest name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name
     * @return name
     */
    @ApiModelProperty(value = "")


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
        EditAnnotationRequest editAnnotationRequest = (EditAnnotationRequest) o;
        return Objects.equals(this.annotationId, editAnnotationRequest.annotationId) &&
                Objects.equals(this.positionX, editAnnotationRequest.positionX) &&
                Objects.equals(this.positionY, editAnnotationRequest.positionY) &&
                Objects.equals(this.positionZ, editAnnotationRequest.positionZ) &&
                Objects.equals(this.radius, editAnnotationRequest.radius) &&
                Objects.equals(this.color, editAnnotationRequest.color) &&
                Objects.equals(this.getAnnotationData().getType(), editAnnotationRequest.getAnnotationData().getType()) &&
                Objects.equals(this.name, editAnnotationRequest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotationId, positionX, positionY, positionZ, radius, color, annotationData.getType(), name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EditAnnotationRequest {\n");

        sb.append("    annotationId: ").append(toIndentedString(annotationId)).append("\n");
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

