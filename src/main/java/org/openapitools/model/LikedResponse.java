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
 * LikedResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-02T23:11:24.591124300+08:00[Asia/Shanghai]")

public class LikedResponse   {
    @JsonProperty("sceneId")
    private Integer sceneId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("isLiked")
    private Boolean isLiked;

    @JsonProperty("sceneLikedCount")
    private Integer sceneLikedCount;

    public LikedResponse sceneId(Integer sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    /**
     * 场景id
     * @return sceneId
     */
    @ApiModelProperty(required = true, value = "场景id")
    @NotNull


    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public LikedResponse userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    /**
     * 用户id
     * @return userId
     */
    @ApiModelProperty(required = true, value = "用户id")
    @NotNull


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LikedResponse isLiked(Boolean isLiked) {
        this.isLiked = isLiked;
        return this;
    }

    /**
     * 是否点赞
     * @return isLiked
     */
    @ApiModelProperty(required = true, value = "是否点赞")
    @NotNull


    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public LikedResponse sceneLikedCount(Integer sceneLikedCount) {
        this.sceneLikedCount = sceneLikedCount;
        return this;
    }

    /**
     * 当前场景点赞总数
     * @return sceneLikedCount
     */
    @ApiModelProperty(required = true, value = "当前场景点赞总数")
    @NotNull


    public Integer getSceneLikedCount() {
        return sceneLikedCount;
    }

    public void setSceneLikedCount(Integer sceneLikedCount) {
        this.sceneLikedCount = sceneLikedCount;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LikedResponse likedResponse = (LikedResponse) o;
        return Objects.equals(this.sceneId, likedResponse.sceneId) &&
                Objects.equals(this.userId, likedResponse.userId) &&
                Objects.equals(this.isLiked, likedResponse.isLiked) &&
                Objects.equals(this.sceneLikedCount, likedResponse.sceneLikedCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, userId, isLiked, sceneLikedCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LikedResponse {\n");

        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    isLiked: ").append(toIndentedString(isLiked)).append("\n");
        sb.append("    sceneLikedCount: ").append(toIndentedString(sceneLikedCount)).append("\n");
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

