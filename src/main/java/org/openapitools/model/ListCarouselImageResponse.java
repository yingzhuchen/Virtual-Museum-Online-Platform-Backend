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
 * ListCarouselImageResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T08:40:56.565391+08:00[Asia/Shanghai]")

public class ListCarouselImageResponse   {
  @JsonProperty("totalCount")
  private Integer totalCount;

  @JsonProperty("data")
  @Valid
  private List<CarouselImageResponse> data = new ArrayList<>();

  public ListCarouselImageResponse totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * 符合查询条件的结果总数
   * @return totalCount
  */
  @ApiModelProperty(required = true, value = "符合查询条件的结果总数")
  @NotNull


  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public ListCarouselImageResponse data(List<CarouselImageResponse> data) {
    this.data = data;
    return this;
  }

  public ListCarouselImageResponse addDataItem(CarouselImageResponse dataItem) {
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<CarouselImageResponse> getData() {
    return data;
  }

  public void setData(List<CarouselImageResponse> data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListCarouselImageResponse listCarouselImageResponse = (ListCarouselImageResponse) o;
    return Objects.equals(this.totalCount, listCarouselImageResponse.totalCount) &&
        Objects.equals(this.data, listCarouselImageResponse.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalCount, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListCarouselImageResponse {\n");
    
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

