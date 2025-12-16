package org.openapitools.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import org.openapitools.entity.User;
import org.openapitools.entity.UserSub;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UpdateUserInfoResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-03T11:36:07.345+08:00[Asia/Shanghai]")

public class UpdateUserInfoResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("avaterUrl")
    private String avaterUrl;

    @JsonProperty("nick_name")
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

    public UpdateUserInfoResponse name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 用户名，名称
     *
     * @return name
     */
    @ApiModelProperty(value = "用户名，名称")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UpdateUserInfoResponse avaterUrl(String avaterUrl) {
        this.avaterUrl = avaterUrl;
        return this;
    }

    /**
     * 头像链接
     *
     * @return avaterUrl
     */
    @ApiModelProperty(value = "头像链接")


    public String getAvaterUrl() {
        return avaterUrl;
    }

    public void setAvaterUrl(String avaterUrl) {
        this.avaterUrl = avaterUrl;
    }

    public UpdateUserInfoResponse nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    /**
     * 昵称
     *
     * @return nickName
     */
    @ApiModelProperty(value = "昵称")


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UpdateUserInfoResponse motto(String motto) {
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


    public UpdateUserInfoResponse gender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    /**
     * 性别
     *
     * @return gender
     */
    @ApiModelProperty(value = "性别")


    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public UpdateUserInfoResponse birthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    /**
     * 生日
     *
     * @return birthday
     */
    @ApiModelProperty(value = "生日")


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public UpdateUserInfoResponse address(String address) {
        this.address = address;
        return this;
    }

    /**
     * 地址
     *
     * @return address
     */
    @ApiModelProperty(value = "地址")


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public UpdateUserInfoResponse topSceneIdList(List<TopSceneId> topSceneIdList) {
        this.topSceneIdList = topSceneIdList;
        return this;
    }

    public UpdateUserInfoResponse addTopSceneIdListItem(TopSceneId topSceneIdListItem) {
        if (this.topSceneIdList == null) {
            this.topSceneIdList = new ArrayList<>();
        }
        this.topSceneIdList.add(topSceneIdListItem);
        return this;
    }

    /**
     * 置顶场景id列表，不能超过3个
     * @return topSceneIdList
     */
    @ApiModelProperty(value = "置顶场景id列表，不能超过3个")

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
        UpdateUserInfoResponse updateUserInfoResponse = (UpdateUserInfoResponse) o;
        return Objects.equals(this.name, updateUserInfoResponse.name) &&
                Objects.equals(this.avaterUrl, updateUserInfoResponse.avaterUrl) &&
                Objects.equals(this.nickName, updateUserInfoResponse.nickName) &&
                Objects.equals(this.motto, updateUserInfoResponse.motto) &&
                Objects.equals(this.gender, updateUserInfoResponse.gender) &&
                Objects.equals(this.birthday, updateUserInfoResponse.birthday) &&
                Objects.equals(this.address, updateUserInfoResponse.address) &&
                Objects.equals(this.topSceneIdList, updateUserInfoResponse.topSceneIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avaterUrl, nickName, motto, gender, birthday, address, topSceneIdList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateUserInfoResponse {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    avaterUrl: ").append(toIndentedString(avaterUrl)).append("\n");
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




public void setUser(User user, UserSub userSub) {
        this.name = user.getUserName();
        this.avaterUrl = user.getAvatarUrl();
        this.nickName = userSub.getNickName();
        this.motto = userSub.getMotto();
        switch ((int)userSub.getGender()) {
            case 1:
                this.gender = GenderEnum.MALE;
                break;
            case 2:
                this.gender = GenderEnum.FEMALE;
                break;
            case 3:
                this.gender = GenderEnum.UNKOWN;
                break;
        }

        this.birthday = String.valueOf(userSub.getBirthday());
        this.address = userSub.getAddress();

        this.topSceneIdList = user.getTopSceneId();
    }
}

