package org.openapitools.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListAnnotationResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-15T16:35:29.532898300+08:00[Asia/Shanghai]")
@Data
public class ListAnnotationResponse {
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("data")
    @Valid
    private List<AnnotationResponse> data = new ArrayList<>();


    public ListAnnotationResponse totalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    /**
     * Get totalCount
     *
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

    public ListAnnotationResponse data(List<AnnotationResponse> data) {
        this.data = data;
        return this;
    }

    public ListAnnotationResponse addDataItem(AnnotationResponse dataItem) {
        this.data.add(dataItem);
        return this;
    }

    /**
     * Get data
     *
     * @return data
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public List<AnnotationResponse> getData() {
        return data;
    }

    public void setData(List<AnnotationResponse> data) {
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
        ListAnnotationResponse listAnnotationResponse = (ListAnnotationResponse) o;
        return Objects.equals(this.totalCount, listAnnotationResponse.totalCount) &&
                Objects.equals(this.data, listAnnotationResponse.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCount, data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListAnnotationResponse {\n");

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

