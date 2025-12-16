package org.openapitools.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.entity.Scene;

import javax.validation.Valid;
import javax.validation.constraints.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


/**
 * Scene
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-23T20:50:19.184+08:00[Asia/Shanghai]")

public class SceneResponse {
    @JsonProperty("id")
    private long id;

    @JsonProperty("creatorId")
    private long creatorId;

    @JsonProperty("createTime")
    private LocalDateTime createTime;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("coverUrl")
    private String coverUrl;

    @JsonProperty("collaborators") // 协作者
    private List<Long> collaborators;

    @JsonProperty("isDeleted")
    private Boolean isDeleted;

    @JsonProperty("isAudit")
    private int isAudit;

    @JsonProperty("isPublic")
    private Boolean isPublic;

    @JsonProperty("tags")
    @Valid
    private List<String> tags = new ArrayList<>();

    @JsonProperty("birthPosition")
    private Vector3 birthPosition;

    @JsonProperty("birthRotation")
    private Vector3 birthRotation;
    @JsonProperty("audioUrl")
    private String audioUrl;

    public SceneResponse id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 场景 ID
     *
     * @return id
     */
    @ApiModelProperty(required = true, value = "场景 ID")
    @NotNull


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SceneResponse creatorId(long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    /**
     * 场景创建者 ID
     *
     * @return creatorId
     */
    @ApiModelProperty(required = true, value = "场景创建者 ID")
    @NotNull


    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public SceneResponse createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 创建时间，精确到毫秒的时间戳
     *
     * @return createTime
     */
    @ApiModelProperty(required = true, value = "创建时间，精确到毫秒的时间戳")
    @NotNull


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public SceneResponse name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 场景名
     *
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

    public SceneResponse url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 场景的 Luma 链接，如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`
     *
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

    public SceneResponse description(String description) {
        this.description = description;
        return this;
    }

    /**
     * 场景描述
     *
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


    /**
     * 场景协作者的ID列表
     *
     * @return collaborators
     */
    @ApiModelProperty(value = "场景协作者的ID列表")
    @Valid
    public List<Long> getCollaborators() {
        return collaborators;
    }

    /**
     * 设置场景协作者的ID列表
     *
     * @param collaborators 场景协作者的ID列表
     */
    public void setCollaborators(List<Long> collaborators) {
        this.collaborators = collaborators;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public SceneResponse isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    /**
     * 场景是否公开可见
     *
     * @return isPublic
     */
    @ApiModelProperty(required = true, value = "场景是否公开可见")
    @NotNull


    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public SceneResponse isAudit(Integer isAudit) {
        this.isAudit = isAudit;
        return this;
    }

    /**
     * 审核状态，数据库使用tinyint实现，0未审核，1审核通过，2审核拒绝
     *
     * @return isAudit
     */
    @ApiModelProperty(required = true, value = "审核状态，数据库使用tinyint实现，0未审核，1审核通过，2审核拒绝")
    @NotNull


    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public SceneResponse isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    /**
     * 场景是否被删除
     *
     * @return isDeleted
     */
    @ApiModelProperty(required = true, value = "场景是否被删除")
    @NotNull


    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public SceneResponse addTagsItem(String tagsItem) {
        this.tags.add(tagsItem);
        return this;
    }

    /**
     * 标签
     *
     * @return tags
     */
    @ApiModelProperty(required = true, value = "标签")
    @NotNull


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public SceneResponse birthPosition(Vector3 birthPosition) {
        this.birthPosition = birthPosition;
        return this;
    }

    /**
     * Get birthPosition
     *
     * @return birthPosition
     */
    @ApiModelProperty(value = "")

    @Valid

    public Vector3 getBirthPosition() {
        return birthPosition;
    }

    public void setBirthPosition(Vector3 birthPosition) {
        this.birthPosition = birthPosition;
    }

    public SceneResponse birthRotation(Vector3 birthRotation) {
        this.birthRotation = birthRotation;
        return this;
    }

    /**
     * Get birthRotation
     *
     * @return birthRotation
     */
    @ApiModelProperty(value = "")

    @Valid

    public Vector3 getBirthRotation() {
        return birthRotation;
    }

    public void setBirthRotation(Vector3 birthRotation) {
        this.birthRotation = birthRotation;
    }


    public SceneResponse audioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        return this;
    }

    /**
     * 音频链接
     *
     * @return audioUrl
     */
    @ApiModelProperty(value = "音频链接")


    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SceneResponse sceneResponse = (SceneResponse) o;
        return Objects.equals(this.id, sceneResponse.id) &&
                Objects.equals(this.creatorId, sceneResponse.creatorId) &&
                Objects.equals(this.createTime, sceneResponse.createTime) &&
                Objects.equals(this.name, sceneResponse.name) &&
                Objects.equals(this.url, sceneResponse.url) &&
                Objects.equals(this.description, sceneResponse.description) &&
                Objects.equals(this.isPublic, sceneResponse.isPublic) &&
                Objects.equals(this.isAudit, sceneResponse.isAudit) &&
                Objects.equals(this.isDeleted, sceneResponse.isDeleted) &&
                Objects.equals(this.collaborators, sceneResponse.collaborators) &&
                Objects.equals(this.coverUrl, sceneResponse.coverUrl) &&
                Objects.equals(this.tags, sceneResponse.tags) &&
                Objects.equals(this.birthPosition, sceneResponse.birthPosition) &&
                Objects.equals(this.birthRotation, sceneResponse.birthRotation) &&
                Objects.equals(this.audioUrl, sceneResponse.audioUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creatorId, createTime, name, url, description, isPublic, isAudit, isDeleted, collaborators, coverUrl, tags, birthPosition, birthRotation);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SceneResponse {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    creatorId: ").append(toIndentedString(creatorId)).append("\n");
        sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    isPublic: ").append(toIndentedString(isPublic)).append("\n");
        sb.append("    isAudit: ").append(toIndentedString(isAudit)).append("\n");
        sb.append("    isDeleted: ").append(toIndentedString(isDeleted)).append("\n");
        sb.append("    collaborators: ").append(toIndentedString(collaborators)).append("\n");
        sb.append("    coverUrl: ").append(toIndentedString(coverUrl)).append("\n");
        sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
        sb.append("    birthPosition: ").append(toIndentedString(birthPosition)).append("\n");
        sb.append("    birthRotation: ").append(toIndentedString(birthRotation)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    public void setScene(Scene scene) {
        this.id = scene.getSceneId();
        this.creatorId = scene.getSceneCreatorId();
        this.createTime = scene.getSceneCreateTime();
        this.name = scene.getSceneName();
        this.url = scene.getSceneUrl();
        this.description = scene.getSceneDescription();

        // 直接从 JSON 字符串中解析出协作者列表
        String collaboratorsJson = scene.getCollaborators();
        if (collaboratorsJson != null && !collaboratorsJson.isEmpty()) {
            try {
                JSONArray collaboratorsArray = JSON.parseArray(collaboratorsJson);
                List<Long> collaboratorIds = new ArrayList<>();
                for (Object obj : collaboratorsArray) {
                    JSONObject collaborator = (JSONObject) obj;
                    collaboratorIds.add(collaborator.getLong("collaborator_id"));
                }
                this.collaborators = collaboratorIds;
            } catch (JSONException e) {
                e.printStackTrace();
                // 处理异常，例如日志记录或者抛出运行时异常
            }
        }
        if (scene.getSceneIsDeleted() == 1)
            this.isDeleted = true;
        else this.isDeleted = false;
        this.isAudit = scene.getSceneIsAudit();
        if (scene.getSceneIsPublic() == 1)
            this.isPublic = true;
        else
            this.isPublic = false;
        this.coverUrl = scene.getSceneCoverUrl();
        this.tags = scene.getTagsList();
        // birthPosition和birthRotation设置默认值
        Vector3 birthPositionTmp = scene.getBirthPosition();
        Vector3 birthRotationTmp = scene.getBirthRotation();
        if (birthPositionTmp == null) {
            birthPositionTmp = new Vector3();
            birthPositionTmp.setX(BigDecimal.ZERO);
            birthPositionTmp.setY(BigDecimal.ZERO);
            birthPositionTmp.setZ(BigDecimal.ZERO);
        }
        if (birthRotationTmp == null) {
            birthRotationTmp = new Vector3();
            birthRotationTmp.setX(BigDecimal.ZERO);
            birthRotationTmp.setY(BigDecimal.ZERO);
            birthRotationTmp.setZ(BigDecimal.ZERO);
        }

        this.birthPosition = birthPositionTmp;
        this.birthRotation = birthRotationTmp;
        this.audioUrl=scene.getAudioUrl();

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

