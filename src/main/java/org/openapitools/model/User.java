package org.openapitools.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;

@ApiModel(description = "用户信息表")
@Table(name = "User_Info")
public class User {

    @JsonProperty("User_Id")
    private Integer userId;

    @JsonProperty("Role_Id")
    private Integer roleId = 1;

    @JsonProperty("User_Name")
    private String userName;

    @JsonProperty("User_Password")
    private String userPassword;

    @JsonProperty("User_Version")
    private Integer userVersion;

    @JsonProperty("User_Is_Deleted")
    private Integer userIsDeleted;

    public User userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User roleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    @ApiModelProperty(value = "角色id", required = true)
    @NotNull
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public User userName(String userName) {
        this.userName = userName;
        return this;
    }

    @ApiModelProperty(value = "用户名", required = true)
    @NotNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User userPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    @ApiModelProperty(value = "用户密码", required = true)
    @NotNull
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public User userVersion(Integer userVersion) {
        this.userVersion = userVersion;
        return this;
    }

    @ApiModelProperty(value = "version状态", required = true)
    @NotNull
    public Integer getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(Integer userVersion) {
        this.userVersion = userVersion;
    }

    public User userIsDeleted(Integer userIsDeleted) {
        this.userIsDeleted = userIsDeleted;
        return this;
    }

    @ApiModelProperty(value = "删除状态")
    public Integer getUserIsDeleted() {
        return userIsDeleted;
    }

    public void setUserIsDeleted(Integer userIsDeleted) {
        this.userIsDeleted = userIsDeleted;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.userId, user.userId) &&
                Objects.equals(this.roleId, user.roleId) &&
                Objects.equals(this.userName, user.userName) &&
                Objects.equals(this.userPassword, user.userPassword) &&
                Objects.equals(this.userVersion, user.userVersion) &&
                Objects.equals(this.userIsDeleted, user.userIsDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId, userName, userPassword, userVersion, userIsDeleted);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    roleId: ").append(toIndentedString(roleId)).append("\n");
        sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
        sb.append("    userPassword: ").append(toIndentedString(userPassword)).append("\n");
        sb.append("    userVersion: ").append(toIndentedString(userVersion)).append("\n");
        sb.append("    userIsDeleted: ").append(toIndentedString(userIsDeleted)).append("\n");
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
