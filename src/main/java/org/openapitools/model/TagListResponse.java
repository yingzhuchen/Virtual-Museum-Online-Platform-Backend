package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.TagsModel;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TagListResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-04T17:08:18.358+08:00[Asia/Shanghai]")

public class TagListResponse   {
  @JsonProperty("tagsList")
  @Valid
  private List<TagsModel> tagsList = new ArrayList<>();

  public TagListResponse tagsList(List<TagsModel> tagsList) {
    this.tagsList = tagsList;
    return this;
  }

  public TagListResponse addTagsListItem(TagsModel tagsListItem) {
    this.tagsList.add(tagsListItem);
    return this;
  }

  /**
   * ID 编号
   * @return tagsList
  */
  @ApiModelProperty(required = true, value = "ID 编号")
  @NotNull

  @Valid

  public List<TagsModel> getTagsList() {
    return tagsList;
  }

  public void setTagsList(List<TagsModel> tagsList) {
    this.tagsList = tagsList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagListResponse tagListResponse = (TagListResponse) o;
    return Objects.equals(this.tagsList, tagListResponse.tagsList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tagsList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagListResponse {\n");
    
    sb.append("    tagsList: ").append(toIndentedString(tagsList)).append("\n");
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

