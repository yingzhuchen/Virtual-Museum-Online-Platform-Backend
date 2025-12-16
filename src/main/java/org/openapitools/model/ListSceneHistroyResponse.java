package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.SceneHistoryResponse;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListSceneHistroyResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-08T19:39:08.022778900+08:00[Asia/Shanghai]")

public class ListSceneHistroyResponse   {
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("data")
    @Valid
    private List<SceneHistoryResponse> data = new ArrayList<>();

    public ListSceneHistroyResponse totalCount(Integer totalCount) {
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

    public ListSceneHistroyResponse data(List<SceneHistoryResponse> data) {
        this.data = data;
        return this;
    }

    public ListSceneHistroyResponse addDataItem(SceneHistoryResponse dataItem) {
        this.data.add(dataItem);
        return this;
    }

    /**
     * Get data
     * @return data
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public List<SceneHistoryResponse> getData() {
        return data;
    }

    public void setData(List<SceneHistoryResponse> data) {
        this.data = data;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListSceneHistroyResponse listSceneHistroyResponse = (ListSceneHistroyResponse) o;
        return Objects.equals(this.totalCount, listSceneHistroyResponse.totalCount) &&
                Objects.equals(this.data, listSceneHistroyResponse.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListSceneHistroyResponse {\n");

        sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

