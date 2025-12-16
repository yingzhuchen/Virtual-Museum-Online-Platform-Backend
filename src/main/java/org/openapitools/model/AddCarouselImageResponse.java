package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.CarouselImageResponse;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddCarouselImageResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-09T16:08:25.383+08:00[Asia/Shanghai]")

public class AddCarouselImageResponse   {
  @JsonProperty("CarouselImageList")
  @Valid
  private List<CarouselImageResponse> carouselImageList = new ArrayList<>();

  public AddCarouselImageResponse carouselImageList(List<CarouselImageResponse> carouselImageList) {
    this.carouselImageList = carouselImageList;
    return this;
  }

  public AddCarouselImageResponse addCarouselImageListItem(CarouselImageResponse carouselImageListItem) {
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

  public List<CarouselImageResponse> getCarouselImageList() {
    return carouselImageList;
  }

  public void setCarouselImageList(List<CarouselImageResponse> carouselImageList) {
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
    AddCarouselImageResponse addCarouselImageResponse = (AddCarouselImageResponse) o;
    return Objects.equals(this.carouselImageList, addCarouselImageResponse.carouselImageList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(carouselImageList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddCarouselImageResponse {\n");
    
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

