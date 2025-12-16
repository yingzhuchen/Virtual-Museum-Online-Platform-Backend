package org.openapitools.model.modelFactory;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.Valid;

/**
 * FavoriteSceneCrudRequestBody
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-22T16:47:40.964519600+08:00[Asia/Shanghai]")

public class FavoriteSceneCrudRequestBody   {
    @JsonProperty("existingFavouritesName")
    private String existingFavouritesName;

    @JsonProperty("sceneId")
    private Integer sceneId;

    @JsonProperty("sceneIdList")
    @Valid
    private List<Integer> sceneIdList = null;

    public FavoriteSceneCrudRequestBody existingFavouritesName(String existingFavouritesName) {
        this.existingFavouritesName = existingFavouritesName;
        return this;
    }

    /**
     * Get existingFavouritesName
     * @return existingFavouritesName
     */
    @ApiModelProperty(value = "")


    public String getExistingFavouritesName() {
        return existingFavouritesName;
    }

    public void setExistingFavouritesName(String existingFavouritesName) {
        this.existingFavouritesName = existingFavouritesName;
    }

    public FavoriteSceneCrudRequestBody sceneId(Integer sceneId) {
        this.sceneId = sceneId;
        return this;
    }

    /**
     * Get sceneId
     * @return sceneId
     */
    @ApiModelProperty(value = "")


    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public FavoriteSceneCrudRequestBody sceneIdList(List<Integer> sceneIdList) {
        this.sceneIdList = sceneIdList;
        return this;
    }

    public FavoriteSceneCrudRequestBody addSceneIdListItem(Integer sceneIdListItem) {
        if (this.sceneIdList == null) {
            this.sceneIdList = new ArrayList<>();
        }
        this.sceneIdList.add(sceneIdListItem);
        return this;
    }

    /**
     * Get sceneIdList
     * @return sceneIdList
     */
    @ApiModelProperty(value = "")


    public List<Integer> getSceneIdList() {
        return sceneIdList;
    }

    public void setSceneIdList(List<Integer> sceneIdList) {
        this.sceneIdList = sceneIdList;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavoriteSceneCrudRequestBody favoriteSceneCrudRequestBody = (FavoriteSceneCrudRequestBody) o;
        return Objects.equals(this.existingFavouritesName, favoriteSceneCrudRequestBody.existingFavouritesName) &&
                Objects.equals(this.sceneId, favoriteSceneCrudRequestBody.sceneId) &&
                Objects.equals(this.sceneIdList, favoriteSceneCrudRequestBody.sceneIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(existingFavouritesName, sceneId, sceneIdList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FavoriteSceneCrudRequestBody {\n");

        sb.append("    existingFavouritesName: ").append(toIndentedString(existingFavouritesName)).append("\n");
        sb.append("    sceneId: ").append(toIndentedString(sceneId)).append("\n");
        sb.append("    sceneIdList: ").append(toIndentedString(sceneIdList)).append("\n");
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

    public boolean hasSamePropertyOf(Object b){
        final BeanWrapper srcOfA = new BeanWrapperImpl(this);
        final BeanWrapper srcOfB = new BeanWrapperImpl(b);
        PropertyDescriptor[] pdsOfA = srcOfA.getPropertyDescriptors();
        PropertyDescriptor[] pdsOfB = srcOfB.getPropertyDescriptors();
        for(PropertyDescriptor pdi:pdsOfA){
            if(srcOfA.getPropertyValue(pdi.getName())==null) continue;//仅提取非空字段
            boolean isContain=false;
            for(PropertyDescriptor pdj:pdsOfB){
                if(pdi.getName().equals(pdj.getName())) isContain=true;
            }
            if(!isContain) return false;
        }
        for(PropertyDescriptor pdi:pdsOfB){
            boolean isContain=false;
            for(PropertyDescriptor pdj:pdsOfA){
                if((pdi.getName().equals(pdj.getName())&&
                        (srcOfA.getPropertyValue(pdi.getName())!=null))) isContain=true;//仅提取非空字段
            }
            if(!isContain) return false;
        }
        return true;
    }

}

