package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.GetUserInfoResponse;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListUserInfoResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-18T09:30:46.487275900+08:00[Asia/Shanghai]")

public class ListUserInfoResponse   {
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("data")
    @Valid
    private List<GetUserInfoResponse> data = new ArrayList<>();

    public ListUserInfoResponse totalCount(Integer totalCount) {
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

    public ListUserInfoResponse data(List<GetUserInfoResponse> data) {
        this.data = data;
        return this;
    }

    public ListUserInfoResponse addDataItem(GetUserInfoResponse dataItem) {
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

    public List<GetUserInfoResponse> getData() {
        return data;
    }

    public void setData(List<GetUserInfoResponse> data) {
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
        ListUserInfoResponse listUserInfoResponse = (ListUserInfoResponse) o;
        return Objects.equals(this.totalCount, listUserInfoResponse.totalCount) &&
                Objects.equals(this.data, listUserInfoResponse.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListUserInfoResponse {\n");

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

