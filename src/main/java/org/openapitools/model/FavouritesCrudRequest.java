package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.model.modelFactory.FavouritesCrudRequestBody;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * FavouritesCrudRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-16T20:12:46.284075100+08:00[Asia/Shanghai]")

public class FavouritesCrudRequest   {
    @JsonProperty("operation")
    private OperationType operation;

    @JsonProperty("body")
    private FavouritesCrudRequestBody body;

    public FavouritesCrudRequest operation(OperationType operation) {
        this.operation = operation;
        return this;
    }

    /**
     * Get operation
     * @return operation
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public FavouritesCrudRequest body(FavouritesCrudRequestBody body) {
        this.body = body;
        return this;
    }

    /**
     * Get body
     * @return body
     */
    @ApiModelProperty(value = "")

    @Valid

    public FavouritesCrudRequestBody getBody() {
        return body;
    }

    public void setBody(FavouritesCrudRequestBody body) {
        this.body = body;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavouritesCrudRequest favouritesCrudRequest = (FavouritesCrudRequest) o;
        return Objects.equals(this.operation, favouritesCrudRequest.operation) &&
                Objects.equals(this.body, favouritesCrudRequest.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FavouritesCrudRequest {\n");

        sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
        sb.append("    body: ").append(toIndentedString(body)).append("\n");
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

