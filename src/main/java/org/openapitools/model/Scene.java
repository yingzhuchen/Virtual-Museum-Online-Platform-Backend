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
 * Scene
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-23T20:50:19.184+08:00[Asia/Shanghai]")

public class Scene   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("creatorId")
  private Integer creatorId;

  @JsonProperty("createTime")
  private Integer createTime;

  @JsonProperty("name")
  private String name;

  @JsonProperty("url")
  private String url;

  @JsonProperty("description")
  private String description;

  public Scene id(Integer id) {
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

  public Scene creatorId(Integer creatorId) {
    this.creatorId = creatorId;
    return this;
  }

  /**
   * 场景创建者 ID
   * @return creatorId
  */
  @ApiModelProperty(required = true, value = "场景创建者 ID")
  @NotNull


  public Integer getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Integer creatorId) {
    this.creatorId = creatorId;
  }

  public Scene createTime(Integer createTime) {
    this.createTime = createTime;
    return this;
  }

  /**
   * 创建时间，精确到毫秒的时间戳
   * @return createTime
  */
  @ApiModelProperty(required = true, value = "创建时间，精确到毫秒的时间戳")
  @NotNull


  public Integer getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Integer createTime) {
    this.createTime = createTime;
  }

  public Scene name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 场景名
   * @return name
  */
  @ApiModelProperty(required = true, value = "场景名")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Scene url(String url) {
    this.url = url;
    return this;
  }

  /**
   * 场景的 Luma 链接，如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`
   * @return url
  */
  @ApiModelProperty(required = true, value = "场景的 Luma 链接，如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`")
  @NotNull


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Scene description(String description) {
    this.description = description;
    return this;
  }

  /**
   * 场景描述
   * @return description
  */
  @ApiModelProperty(required = true, value = "场景描述")
  @NotNull


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Scene scene = (Scene) o;
    return Objects.equals(this.id, scene.id) &&
        Objects.equals(this.creatorId, scene.creatorId) &&
        Objects.equals(this.createTime, scene.createTime) &&
        Objects.equals(this.name, scene.name) &&
        Objects.equals(this.url, scene.url) &&
        Objects.equals(this.description, scene.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, creatorId, createTime, name, url, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Scene {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    creatorId: ").append(toIndentedString(creatorId)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

