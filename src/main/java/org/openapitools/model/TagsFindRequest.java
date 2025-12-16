package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * TagsFindRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-04T17:40:26.698+08:00[Asia/Shanghai]")

public class TagsFindRequest   {
  @JsonProperty("tagName")
  private String tagName;

  public TagsFindRequest tagName(String tagName) {
    this.tagName = tagName;
    return this;
  }

  /**
   * 标签名，名称
   * @return tagName
  */
  @ApiModelProperty(required = true, value = "标签名，名称")
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
    TagsFindRequest tagsFindRequest = (TagsFindRequest) o;
    return Objects.equals(this.tagName, tagsFindRequest.tagName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tagName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagsFindRequest {\n");
    
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

