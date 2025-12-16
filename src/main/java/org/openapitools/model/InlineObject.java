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
 * InlineObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-27T21:34:01.227839700+08:00[Asia/Shanghai]")

public class InlineObject   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonProperty("description")
  private String description;

  @JsonProperty("isPublic")
  private Boolean isPublic;

  public InlineObject id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * 场景 ID
   * @return id
  */
  @ApiModelProperty(required = true, value = "场景 ID")
  @NotNull


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public InlineObject name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 场景名
   * @return name
  */
  @ApiModelProperty(value = "场景名")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InlineObject url(String url) {
    this.url = url;
    return this;
  }

  /**
   * 场景的 Luma 链接，如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`
   * @return url
  */
  @ApiModelProperty(value = "场景的 Luma 链接，如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public InlineObject description(String description) {
    this.description = description;
    return this;
  }

  /**
   * 场景描述
   * @return description
  */
  @ApiModelProperty(value = "场景描述")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public InlineObject isPublic(Boolean isPublic) {
    this.isPublic = isPublic;
    return this;
  }

  /**
   * 场景是否公开可见
   * @return isPublic
  */
  @ApiModelProperty(value = "场景是否公开可见")


  public Boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineObject inlineObject = (InlineObject) o;
    return Objects.equals(this.id, inlineObject.id) &&
        Objects.equals(this.name, inlineObject.name) &&
        Objects.equals(this.url, inlineObject.url) &&
        Objects.equals(this.description, inlineObject.description) &&
        Objects.equals(this.isPublic, inlineObject.isPublic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, url, description, isPublic);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineObject {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isPublic: ").append(toIndentedString(isPublic)).append("\n");
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

