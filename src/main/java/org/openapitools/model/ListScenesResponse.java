package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListScenesResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-23T20:50:19.184+08:00[Asia/Shanghai]")

public class ListScenesResponse   {
  @JsonProperty("totalCount")
  private BigDecimal totalCount;

  @JsonProperty("data")
  @Valid
  private List<SceneResponse> data = new ArrayList<>();

  public ListScenesResponse totalCount(BigDecimal totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * 此符合此查询条件的结果总数（不止 `data` 里面的数据）
   * @return totalCount
  */
  @ApiModelProperty(required = true, value = "此符合此查询条件的结果总数（不止 `data` 里面的数据）")
  @NotNull

  @Valid

  public BigDecimal getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(BigDecimal totalCount) {
    this.totalCount = totalCount;
  }

  public ListScenesResponse data(List<SceneResponse> data) {
    this.data = data;
    return this;
  }

  public ListScenesResponse addDataItem(SceneResponse dataItem) {
    this.data.add(dataItem);
    return this;
  }

  /**
   * 场景列表
   * @return data
  */
  @ApiModelProperty(required = true, value = "场景列表")
  @NotNull

  @Valid

  public List<SceneResponse> getData() {
    return data;
  }

  public void setData(List<SceneResponse> data) {
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
    ListScenesResponse listScenesResponse = (ListScenesResponse) o;
    return Objects.equals(this.totalCount, listScenesResponse.totalCount) &&
        Objects.equals(this.data, listScenesResponse.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalCount, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListScenesResponse {\n");
    
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

