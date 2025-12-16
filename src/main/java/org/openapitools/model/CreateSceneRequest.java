package org.openapitools.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateSceneRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-23T20:50:19.184+08:00[Asia/Shanghai]")

public class CreateSceneRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("collaboratorsId") // 协作者
    private List<Long> collaborators;

    @JsonProperty("tags")
    @Valid
    private List<String> tags = null;

    @JsonProperty("cacheImageUrl")
    private String cacheImageUrl;

    @JsonProperty("audioUrl")
    private String audioUrl;

    public CreateSceneRequest name(String name) {
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

    public CreateSceneRequest url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 场景链接，Luma AI 的场景链接（如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`）
     *
     * @return url
     */
    @ApiModelProperty(required = true, value = "场景链接，Luma AI 的场景链接（如 `https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f`）")
    @NotNull


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CreateSceneRequest description(String description) {
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


    public CreateSceneRequest tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public CreateSceneRequest addTagsItem(String tagsItem) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    /**
     * 标签tags
     *
     * @return tags
     */
    @ApiModelProperty(value = "标签tags")


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public CreateSceneRequest cacheImageUrl(String cacheImageUrl) {
        this.cacheImageUrl = cacheImageUrl;
        return this;
    }

    /**
     * 调用 uploadImage 接口返回的临时图片链接
     * @return cacheImageUrl
     */
    @ApiModelProperty(required = true, value = "调用 uploadImage 接口返回的临时图片链接")
    @NotNull


    public String getCacheImageUrl() {
        return cacheImageUrl;
    }

    public void setCacheImageUrl(String cacheImageUrl) {
        this.cacheImageUrl = cacheImageUrl;
    }


    public CreateSceneRequest audioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        return this;
    }

    /**
     * 音频链接
     * @return audioUrl
     */
    @ApiModelProperty(value = "音频链接")


    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }


    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateSceneRequest createSceneRequest = (CreateSceneRequest) o;
        return Objects.equals(this.name, createSceneRequest.name) &&
                Objects.equals(this.url, createSceneRequest.url) &&
                Objects.equals(this.description, createSceneRequest.description) &&
                Objects.equals(this.collaborators, createSceneRequest.collaborators) &&
                Objects.equals(this.tags, createSceneRequest.tags) &&
                Objects.equals(this.cacheImageUrl, createSceneRequest.cacheImageUrl) &&
                Objects.equals(this.audioUrl, createSceneRequest.audioUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, description, collaborators, tags, cacheImageUrl, audioUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateSceneRequest {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    collaboratorsId: ").append(toIndentedString(collaborators)).append("\n");
        sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
        sb.append("    cacheImageUrl: ").append(toIndentedString(cacheImageUrl)).append("\n");
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

