package org.openapitools.model;

import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.entity.SceneHistory;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SceneHistoryResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-08T19:39:08.022778900+08:00[Asia/Shanghai]")

public class SceneHistoryResponse   {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("sceneId")
    private Integer sceneId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("time")
    private LocalDateTime time;

    public SceneHistoryResponse id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * ID 编号
     * @return id
     */
    @ApiModelProperty(required = true, value = "ID 编号")
    @NotNull


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SceneHistoryResponse sceneId(Integer sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    /**
     * Get sceneId
     * @return sceneId
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public SceneHistoryResponse userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get userId
     * @return userId
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public SceneHistoryResponse time(LocalDateTime time) {
        this.time = time;
        return this;
    }

    /**
     * 访问时间
     * @return time
     */
    @ApiModelProperty(required = true, value = "访问时间")
    @NotNull


    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SceneHistoryResponse sceneHistoryResponse = (SceneHistoryResponse) o;
        return Objects.equals(this.id, sceneHistoryResponse.id) &&
                Objects.equals(this.sceneId, sceneHistoryResponse.sceneId) &&
                Objects.equals(this.userId, sceneHistoryResponse.userId) &&
                Objects.equals(this.time, sceneHistoryResponse.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sceneId, userId, time);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SceneHistoryResponse {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    time: ").append(toIndentedString(time)).append("\n");
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

    public void setSceneHistory(SceneHistory h){
        this.id=(int)h.getId();
        this.sceneId=(int)h.getSceneId();
        this.userId=(int)h.getUserId();
        this.time=h.getSceneAccessTime();
    }

}

