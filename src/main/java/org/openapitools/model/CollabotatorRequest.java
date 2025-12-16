package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CollabotatorRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-17T09:10:05.814991900+08:00[Asia/Shanghai]")

public class CollabotatorRequest   {
  @JsonProperty("targetCollaborators")
  @Valid
  private List<Integer> targetCollaborators = new ArrayList<>();

  @JsonProperty("sceneId")
  private Integer sceneId;

  public CollabotatorRequest targetCollaborators(List<Integer> targetCollaborators) {
    this.targetCollaborators = targetCollaborators;
    return this;
  }

  public CollabotatorRequest addTargetCollaboratorsItem(Integer targetCollaboratorsItem) {
    this.targetCollaborators.add(targetCollaboratorsItem);
    return this;
  }

  /**
   * 协助者ID
   * @return targetCollaborators
   */
  @ApiModelProperty(required = true, value = "协助者ID")
  @NotNull


  public List<Integer> getTargetCollaborators() {
    return targetCollaborators;
  }

  public void setTargetCollaborators(List<Integer> targetCollaborators) {
    this.targetCollaborators = targetCollaborators;
  }

  public CollabotatorRequest sceneId(Integer sceneId) {
    this.sceneId = sceneId;
    return this;
  }

  /**
   * 场景ID
   * @return sceneId
   */
  @ApiModelProperty(required = true, value = "场景ID")
  @NotNull


  public Integer getSceneId() {
    return sceneId;
  }

  public void setSceneId(Integer sceneId) {
    this.sceneId = sceneId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollabotatorRequest collabotatorRequest = (CollabotatorRequest) o;
    return Objects.equals(this.targetCollaborators, collabotatorRequest.targetCollaborators) &&
            Objects.equals(this.sceneId, collabotatorRequest.sceneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetCollaborators, sceneId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollabotatorRequest {\n");

    sb.append("    targetCollaborators: ").append(toIndentedString(targetCollaborators)).append("\n");
    sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
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

