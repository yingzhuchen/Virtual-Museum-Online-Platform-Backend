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
 * EditSceneRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-15T12:49:18.599927500+08:00[Asia/Shanghai]")

public class EditSceneRequest {
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

    @JsonProperty("tags")
    @Valid
    private List<String> tags = null;

    @JsonProperty("collaborators")
    @Valid
    private List<Integer> collaborators = null;

    @JsonProperty("cacheImageUrl")
    private String cacheImageUrl;

    @JsonProperty("birthPosition")
    private Vector3 birthPosition;

    @JsonProperty("birthRotation")
    private Vector3 birthRotation;

    @JsonProperty("audioUrl")
    private String audioUrl;

    public EditSceneRequest id(Integer id) {
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EditSceneRequest name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 场景名
     *
     * @return name
     */
    @ApiModelProperty(value = "场景名")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EditSceneRequest url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 场景的 Luma 链接，如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`
     *
     * @return url
     */
    @ApiModelProperty(value = "场景的 Luma 链接，如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`")


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EditSceneRequest description(String description) {
        this.description = description;
        return this;
    }

    /**
     * 场景描述
     *
     * @return description
     */
    @ApiModelProperty(value = "场景描述")


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EditSceneRequest isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    /**
     * 场景是否公开可见
     *
     * @return isPublic
     */
    @ApiModelProperty(value = "场景是否公开可见")


    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public EditSceneRequest tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public EditSceneRequest addTagsItem(String tagsItem) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    /**
     * 标签列表，标签
     *
     * @return tags
     */
    @ApiModelProperty(value = "标签列表，标签")


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public EditSceneRequest collaborators(List<Integer> collaborators) {
        this.collaborators = collaborators;
        return this;
    }

    public EditSceneRequest addCollaboratorsItem(Integer collaboratorsItem) {
        if (this.collaborators == null) {
            this.collaborators = new ArrayList<>();
        }
        this.collaborators.add(collaboratorsItem);
        return this;
    }

    /**
     * 添加协作者
     *
     * @return collaborators
     */
    @ApiModelProperty(value = "添加协作者")


    public List<Integer> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Integer> collaborators) {
        this.collaborators = collaborators;
    }

    public EditSceneRequest cacheImageUrl(String cacheImageUrl) {
        this.cacheImageUrl = cacheImageUrl;
        return this;
    }

    /**
     * 从上传图片接口上传后，在这里使用
     *
     * @return cacheImageUrl
     */
    @ApiModelProperty(value = "从上传图片接口上传后，在这里使用")


    public String getCacheImageUrl() {
        return cacheImageUrl;
    }

    public void setCacheImageUrl(String cacheImageUrl) {
        this.cacheImageUrl = cacheImageUrl;
    }

    public EditSceneRequest birthPosition(Vector3 birthPosition) {
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

    public EditSceneRequest birthRotation(Vector3 birthRotation) {
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

    public EditSceneRequest audioUrl(String audioUrl) {
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
        EditSceneRequest editSceneRequest = (EditSceneRequest) o;
        return Objects.equals(this.id, editSceneRequest.id) &&
                Objects.equals(this.name, editSceneRequest.name) &&
                Objects.equals(this.url, editSceneRequest.url) &&
                Objects.equals(this.description, editSceneRequest.description) &&
                Objects.equals(this.isPublic, editSceneRequest.isPublic) &&
                Objects.equals(this.tags, editSceneRequest.tags) &&
                Objects.equals(this.collaborators, editSceneRequest.collaborators) &&
                Objects.equals(this.cacheImageUrl, editSceneRequest.cacheImageUrl) &&
                Objects.equals(this.birthPosition, editSceneRequest.birthPosition) &&
                Objects.equals(this.birthRotation, editSceneRequest.birthRotation) &&
                Objects.equals(this.audioUrl, editSceneRequest.audioUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, description, isPublic, tags, collaborators, cacheImageUrl, birthPosition, birthRotation);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EditSceneRequest {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    isPublic: ").append(toIndentedString(isPublic)).append("\n");
        sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
        sb.append("    collaborators: ").append(toIndentedString(collaborators)).append("\n");
        sb.append("    cacheImageUrl: ").append(toIndentedString(cacheImageUrl)).append("\n");
        sb.append("    birthPosition: ").append(toIndentedString(birthPosition)).append("\n");
        sb.append("    birthRotation: ").append(toIndentedString(birthRotation)).append("\n");
        sb.append("    audioUrl: ").append(toIndentedString(audioUrl)).append("\n");
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

