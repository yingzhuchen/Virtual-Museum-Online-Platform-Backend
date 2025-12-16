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
 * FavouritesListResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-16T10:21:09.041892600+08:00[Asia/Shanghai]")

public class FavouritesListResponse   {
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("favouritesList")
    @Valid
    private List<String> favouritesList = new ArrayList<>();

    public FavouritesListResponse totalCount(Integer totalCount) {
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

    public FavouritesListResponse favouritesList(List<String> favouritesList) {
        this.favouritesList = favouritesList;
        return this;
    }

    public FavouritesListResponse addFavouritesListItem(String favouritesListItem) {
        this.favouritesList.add(favouritesListItem);
        return this;
    }

    /**
     * Get favouritesList
     * @return favouritesList
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public List<String> getFavouritesList() {
        return favouritesList;
    }

    public void setFavouritesList(List<String> favouritesList) {
        this.favouritesList = favouritesList;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavouritesListResponse favouritesListResponse = (FavouritesListResponse) o;
        return Objects.equals(this.totalCount, favouritesListResponse.totalCount) &&
                Objects.equals(this.favouritesList, favouritesListResponse.favouritesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, favouritesList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FavouritesListResponse {\n");

        sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
        sb.append("    favouritesList: ").append(toIndentedString(favouritesList)).append("\n");
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

