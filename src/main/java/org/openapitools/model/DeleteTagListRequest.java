package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.TagId;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DeleteTagListRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-09T17:38:19.359+08:00[Asia/Shanghai]")

public class DeleteTagListRequest   {
  @JsonProperty("tagList")
  @Valid
  private List<TagId> tagList = new ArrayList<>();

  public DeleteTagListRequest tagList(List<TagId> tagList) {
    this.tagList = tagList;
    return this;
  }

  public DeleteTagListRequest addTagListItem(TagId tagListItem) {
    this.tagList.add(tagListItem);
    return this;
  }

  /**
   * Get tagList
   * @return tagList
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<TagId> getTagList() {
    return tagList;
  }

  public void setTagList(List<TagId> tagList) {
    this.tagList = tagList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteTagListRequest deleteTagListRequest = (DeleteTagListRequest) o;
    return Objects.equals(this.tagList, deleteTagListRequest.tagList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tagList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteTagListRequest {\n");
    
    sb.append("    tagList: ").append(toIndentedString(tagList)).append("\n");
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

