package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * TagsModel
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-04T17:08:18.358+08:00[Asia/Shanghai]")

public class TagsModel   {
  @JsonProperty("tagId")
  private Integer tagId;

  @JsonProperty("tagName")
  private String tagName;

  public TagsModel tagId(Integer tagId) {
    this.tagId = tagId;
    return this;
  }

  /**
   * id
   * @return tagId
  */
  @ApiModelProperty(required = true, value = "id")
  @NotNull


  public Integer getTagId() {
    return tagId;
  }

  public void setTagId(Integer tagId) {
    this.tagId = tagId;
  }

  public TagsModel tagName(String tagName) {
    this.tagName = tagName;
    return this;
  }

  /**
   * tag名字
   * @return tagName
  */
  @ApiModelProperty(required = true, value = "tag名字")
  @NotNull


  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagsModel tagsModel = (TagsModel) o;
    return Objects.equals(this.tagId, tagsModel.tagId) &&
        Objects.equals(this.tagName, tagsModel.tagName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tagId, tagName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagsModel {\n");
    
    sb.append("    tagId: ").append(toIndentedString(tagId)).append("\n");
    sb.append("    tagName: ").append(toIndentedString(tagName)).append("\n");
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

