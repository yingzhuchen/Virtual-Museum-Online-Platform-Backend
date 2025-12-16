package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * UpdatePasswordResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-03T21:53:38.677+08:00[Asia/Shanghai]")

public class UpdatePasswordResponse   {
  @JsonProperty("token")
  private String token;

  @JsonProperty("userId")
  private Integer userId;

  @JsonProperty("userName")
  private String userName;

  public UpdatePasswordResponse token(String token) {
    this.token = token;
    return this;
  }

  /**
   * token
   * @return token
  */
  @ApiModelProperty(required = true, value = "token")
  @NotNull


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UpdatePasswordResponse userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  /**
   * 用户id
   * @return userId
  */
  @ApiModelProperty(required = true, value = "用户id")
  @NotNull


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public UpdatePasswordResponse userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * 名字
   * @return userName
  */
  @ApiModelProperty(required = true, value = "名字")
  @NotNull


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdatePasswordResponse updatePasswordResponse = (UpdatePasswordResponse) o;
    return Objects.equals(this.token, updatePasswordResponse.token) &&
        Objects.equals(this.userId, updatePasswordResponse.userId) &&
        Objects.equals(this.userName, updatePasswordResponse.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, userId, userName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdatePasswordResponse {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
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

