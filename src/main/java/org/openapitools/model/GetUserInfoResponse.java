package org.openapitools.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GetUserInfoResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-25T14:05:32.204915700+08:00[Asia/Shanghai]")

public class GetUserInfoResponse   {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private UserRole role = UserRole.USER;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("motto")
    private String motto;

    /**
     * 性别
     */
    public enum GenderEnum {
        MALE("male"),

        FEMALE("female"),

        UNKOWN("unkown");

        private String value;

        GenderEnum(String value) {
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
        public static GenderEnum fromValue(String value) {
            for (GenderEnum b : GenderEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    @JsonProperty("gender")
    private GenderEnum gender;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("address")
    private String address;

    @JsonProperty("topSceneIdList")
    @Valid
    private List<TopSceneId> topSceneIdList = null;

    public GetUserInfoResponse id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 用户 ID
     * @return id
     */
    @ApiModelProperty(required = true, value = "用户 ID")
    @NotNull


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetUserInfoResponse name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 用户名称
     * @return name
     */
    @ApiModelProperty(required = true, value = "用户名称")
    @NotNull


    public String getName(String userName) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GetUserInfoResponse role(UserRole role) {
        this.role = role;
        return this;
    }

    /**
     * Get role
     * @return role
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull

    @Valid

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public GetUserInfoResponse avatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    /**
     * 头像url
     * @return avatarUrl
     */
    @ApiModelProperty(value = "头像url")


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public GetUserInfoResponse nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    /**
     * 昵称
     * @return nickName
     */
    @ApiModelProperty(value = "昵称")


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public GetUserInfoResponse motto(String motto) {
        this.motto = motto;
        return this;
    }

    /**
     * 签名
     * @return motto
     */
    @ApiModelProperty(value = "签名")


    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public GetUserInfoResponse gender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    /**
     * 性别
     * @return gender
     */
    @ApiModelProperty(value = "性别")


    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(int num) {
        if (num==1)
            this.gender = GenderEnum.MALE;
        else if (num==2) {
            this.gender = GenderEnum.FEMALE;
        } else if (num==3) {
            this.gender = GenderEnum.UNKOWN;
        }
    }

    public GetUserInfoResponse birthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    /**
     * 生日
     * @return birthday
     */
    @ApiModelProperty(value = "生日")


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public GetUserInfoResponse address(String address) {
        this.address = address;
        return this;
    }

    /**
     * 地址
     * @return address
     */
    @ApiModelProperty(value = "地址")


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GetUserInfoResponse topSceneIdList(List<TopSceneId> topSceneIdList) {
        this.topSceneIdList = topSceneIdList;
        return this;
    }

    public GetUserInfoResponse addTopSceneIdListItem(TopSceneId topSceneIdListItem) {
        if (this.topSceneIdList == null) {
            this.topSceneIdList = new ArrayList<>();
        }
        this.topSceneIdList.add(topSceneIdListItem);
        return this;
    }

    /**
     * 置顶场景id列表
     * @return topSceneIdList
     */
    @ApiModelProperty(value = "置顶场景id列表")

    @Valid

    public List<TopSceneId> getTopSceneIdList() {
        return topSceneIdList;
    }

    public void setTopSceneIdList(List<TopSceneId> topSceneIdList) {
        this.topSceneIdList = topSceneIdList;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetUserInfoResponse getUserInfoResponse = (GetUserInfoResponse) o;
        return Objects.equals(this.id, getUserInfoResponse.id) &&
                Objects.equals(this.name, getUserInfoResponse.name) &&
                Objects.equals(this.role, getUserInfoResponse.role) &&
                Objects.equals(this.avatarUrl, getUserInfoResponse.avatarUrl) &&
                Objects.equals(this.nickName, getUserInfoResponse.nickName) &&
                Objects.equals(this.motto, getUserInfoResponse.motto) &&
                Objects.equals(this.gender, getUserInfoResponse.gender) &&
                Objects.equals(this.birthday, getUserInfoResponse.birthday) &&
                Objects.equals(this.address, getUserInfoResponse.address) &&
                Objects.equals(this.topSceneIdList, getUserInfoResponse.topSceneIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role, avatarUrl, nickName, motto, gender, birthday, address, topSceneIdList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GetUserInfoResponse {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    role: ").append(toIndentedString(role)).append("\n");
        sb.append("    avatarUrl: ").append(toIndentedString(avatarUrl)).append("\n");
        sb.append("    nickName: ").append(toIndentedString(nickName)).append("\n");
        sb.append("    motto: ").append(toIndentedString(motto)).append("\n");
        sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
        sb.append("    birthday: ").append(toIndentedString(birthday)).append("\n");
        sb.append("    address: ").append(toIndentedString(address)).append("\n");
        sb.append("    topSceneIdList: ").append(toIndentedString(topSceneIdList)).append("\n");
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