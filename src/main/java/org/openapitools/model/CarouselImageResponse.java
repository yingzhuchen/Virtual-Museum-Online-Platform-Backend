package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CarouselImageResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-09T16:08:25.383+08:00[Asia/Shanghai]")

public class CarouselImageResponse   {
  @JsonProperty("carouselImageId")
  private Long carouselImageId;

  @JsonProperty("creatorId")
  private Long creatorId;

  @JsonProperty("sceneId")
  private Long sceneId;

  @JsonProperty("url")
  private String url;

  @JsonProperty("caption")
  private String caption;

  @JsonProperty("orderIndex")
  private Integer orderIndex;

  public CarouselImageResponse carouselImageId(Long carouselImageId) {
    this.carouselImageId = carouselImageId;
    return this;
  }

  /**
   * 轮播图id
   * @return carouselImageId
  */
  @ApiModelProperty(required = true, value = "轮播图id")
  @NotNull


  public Long getCarouselImageId() {
    return carouselImageId;
  }

  public void setCarouselImageId(Long carouselImageId) {
    this.carouselImageId = carouselImageId;
  }

  public CarouselImageResponse creatorId(Long creatorId) {
    this.creatorId = creatorId;
    return this;
  }

  /**
   * 添加人id
   * @return creatorId
  */
  @ApiModelProperty(required = true, value = "添加人id")
  @NotNull


  public Long getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
  }

  public CarouselImageResponse sceneId(Long sceneId) {
    this.sceneId = sceneId;
    return this;
  }

  /**
   * 关联场景id
   * @return sceneId
  */
  @ApiModelProperty(required = true, value = "关联场景id")
  @NotNull


  public Long getSceneId() {
    return sceneId;
  }

  public void setSceneId(Long sceneId) {
    this.sceneId = sceneId;
  }

  public CarouselImageResponse url(String url) {
    this.url = url;
    return this;
  }

  /**
   * 轮播图url
   * @return url
  */
  @ApiModelProperty(required = true, value = "轮播图url")
  @NotNull


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public CarouselImageResponse caption(String caption) {
    this.caption = caption;
    return this;
  }

  /**
   * 图片的描述说明
   * @return caption
  */
  @ApiModelProperty(value = "图片的描述说明")


  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public CarouselImageResponse orderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
    return this;
  }

  /**
   * 图片在轮播图里的显示顺序，为空，默认为最后一位
   * @return orderIndex
  */
  @ApiModelProperty(value = "图片在轮播图里的显示顺序，为空，默认为最后一位")


  public Integer getOrderIndex() {
    return orderIndex;
  }

  public void setOrderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CarouselImageResponse carouselImageResponse = (CarouselImageResponse) o;
    return Objects.equals(this.carouselImageId, carouselImageResponse.carouselImageId) &&
        Objects.equals(this.creatorId, carouselImageResponse.creatorId) &&
        Objects.equals(this.sceneId, carouselImageResponse.sceneId) &&
        Objects.equals(this.url, carouselImageResponse.url) &&
        Objects.equals(this.caption, carouselImageResponse.caption) &&
        Objects.equals(this.orderIndex, carouselImageResponse.orderIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(carouselImageId, creatorId, sceneId, url, caption, orderIndex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CarouselImageResponse {\n");
    
    sb.append("    carouselImageId: ").append(toIndentedString(carouselImageId)).append("\n");
    sb.append("    creatorId: ").append(toIndentedString(creatorId)).append("\n");
    sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    caption: ").append(toIndentedString(caption)).append("\n");
    sb.append("    orderIndex: ").append(toIndentedString(orderIndex)).append("\n");
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

