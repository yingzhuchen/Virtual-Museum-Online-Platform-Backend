package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LikeSceneRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-05T17:33:49.815935+08:00[Asia/Shanghai]")

public class LikeSceneRequest   {
    @JsonProperty("sceneId")
    private Integer sceneId;

    /**
     * Gets or Sets operationType
     */
    public enum OperationTypeEnum {
        LIKE("like"),

        DISLIKE("dislike"),

        READ("read");

        private String value;

        OperationTypeEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static OperationTypeEnum fromValue(String value) {
            for (OperationTypeEnum b : OperationTypeEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    @JsonProperty("operationType")
    private OperationTypeEnum operationType;

    public LikeSceneRequest sceneId(Integer sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    /**
     * Get sceneId
     * @return sceneId
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public LikeSceneRequest operationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
        return this;
    }

    /**
     * Get operationType
     * @return operationType
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull


    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LikeSceneRequest likeSceneRequest = (LikeSceneRequest) o;
        return Objects.equals(this.sceneId, likeSceneRequest.sceneId) &&
                Objects.equals(this.operationType, likeSceneRequest.operationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId, operationType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LikeSceneRequest {\n");

        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("    operationType: ").append(toIndentedString(operationType)).append("\n");
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

