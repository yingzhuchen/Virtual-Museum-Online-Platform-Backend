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
 * CoverResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-12T09:32:32.832172900+08:00[Asia/Shanghai]")

public class CoverResponse   {
    @JsonProperty("url")
    private String url;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Long getCarouselImageId() {
        return carouselImageId;
    }

    public void setCarouselImageId(Long carouselImageId) {
        this.carouselImageId = carouselImageId;
    }

    @JsonProperty("sceneId")
    private Long sceneId;
    @JsonProperty("orderIndex")
    private Integer orderIndex;
    @JsonProperty("carouselImageId")
    private Long carouselImageId;

    public CoverResponse url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 封面url
     * @return url
     */
    @ApiModelProperty(required = true, value = "封面url")
    @NotNull


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CoverResponse title(String title) {
        this.title = title;
        return this;
    }

    /**
     * 标题，使用对应场景名
     * @return title
     */
    @ApiModelProperty(required = true, value = "标题，使用对应场景名")
    @NotNull


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CoverResponse author(String author) {
        this.author = author;
        return this;
    }

    /**
     * 发布者，使用对应场景的创作者ID或创作者名
     * @return author
     */
    @ApiModelProperty(required = true, value = "发布者，使用对应场景的创作者ID或创作者名")
    @NotNull


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoverResponse coverResponse = (CoverResponse) o;
        return Objects.equals(this.url, coverResponse.url) &&
                Objects.equals(this.title, coverResponse.title) &&
                Objects.equals(this.author, coverResponse.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, author);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CoverResponse {\n");

        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    author: ").append(toIndentedString(author)).append("\n");
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

