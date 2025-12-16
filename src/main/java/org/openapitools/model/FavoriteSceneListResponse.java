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
 * FavoriteSceneListResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-18T00:19:16.141815700+08:00[Asia/Shanghai]")

public class FavoriteSceneListResponse   {
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("favoriteList")
    @Valid
    private List<Long> favoriteList = new ArrayList<>();

    public FavoriteSceneListResponse totalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    /**
     * Get totalCount
     * @return totalCount
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public FavoriteSceneListResponse favoriteList(List<Long> favoriteList) {
        this.favoriteList = favoriteList;
        return this;
    }

    public FavoriteSceneListResponse addFavoriteListItem(Long favoriteListItem) {
        this.favoriteList.add(favoriteListItem);
        return this;
    }

    /**
     * 收集场景ID列表
     * @return favoriteList
     */
    @ApiModelProperty(required = true, value = "收集场景ID列表")
    @NotNull


    public List<Long> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Long> favoriteList) {
        this.favoriteList = favoriteList;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavoriteSceneListResponse favoriteSceneListResponse = (FavoriteSceneListResponse) o;
        return Objects.equals(this.totalCount, favoriteSceneListResponse.totalCount) &&
                Objects.equals(this.favoriteList, favoriteSceneListResponse.favoriteList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, favoriteList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FavoriteSceneListResponse {\n");

        sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
        sb.append("    favoriteList: ").append(toIndentedString(favoriteList)).append("\n");
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

