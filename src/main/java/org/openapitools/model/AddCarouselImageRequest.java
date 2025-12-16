package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.CarouselImage;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddCarouselImageRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-09T16:08:25.383+08:00[Asia/Shanghai]")

public class AddCarouselImageRequest   {
  @JsonProperty("CarouselImageList")
  @Valid
  private List<CarouselImage> carouselImageList = new ArrayList<>();

  public AddCarouselImageRequest carouselImageList(List<CarouselImage> carouselImageList) {
    this.carouselImageList = carouselImageList;
    return this;
  }

  public AddCarouselImageRequest addCarouselImageListItem(CarouselImage carouselImageListItem) {
    this.carouselImageList.add(carouselImageListItem);
    return this;
  }

  /**
   * Get carouselImageList
   * @return carouselImageList
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<CarouselImage> getCarouselImageList() {
    return carouselImageList;
  }

  public void setCarouselImageList(List<CarouselImage> carouselImageList) {
    this.carouselImageList = carouselImageList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddCarouselImageRequest addCarouselImageRequest = (AddCarouselImageRequest) o;
    return Objects.equals(this.carouselImageList, addCarouselImageRequest.carouselImageList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(carouselImageList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddCarouselImageRequest {\n");
    
    sb.append("    carouselImageList: ").append(toIndentedString(carouselImageList)).append("\n");
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

