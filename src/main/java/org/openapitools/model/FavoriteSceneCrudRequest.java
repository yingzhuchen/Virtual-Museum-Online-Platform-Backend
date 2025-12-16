package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.model.modelFactory.FavoriteSceneCrudRequestBody;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * FavoriteSceneCrudRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-18T00:19:16.141815700+08:00[Asia/Shanghai]")

public class FavoriteSceneCrudRequest   {
    @JsonProperty("operation")
    private OperationType operation;

    @JsonProperty("body")
    private FavoriteSceneCrudRequestBody body;

    public FavoriteSceneCrudRequest operation(OperationType operation) {
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

    public FavoriteSceneCrudRequest body(FavoriteSceneCrudRequestBody body) {
        this.body = body;
        return this;
    }

    /**
     * Get body
     * @return body
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public FavoriteSceneCrudRequestBody getBody() {
        return body;
    }

    public void setBody(FavoriteSceneCrudRequestBody body) {
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
        FavoriteSceneCrudRequest favoriteSceneCrudRequest = (FavoriteSceneCrudRequest) o;
        return Objects.equals(this.operation, favoriteSceneCrudRequest.operation) &&
                Objects.equals(this.body, favoriteSceneCrudRequest.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FavoriteSceneCrudRequest {\n");

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

