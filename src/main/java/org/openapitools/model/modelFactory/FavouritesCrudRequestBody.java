package org.openapitools.model.modelFactory;

import java.beans.PropertyDescriptor;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * FavouritesCrudRequestBody
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-16T20:21:04.693075200+08:00[Asia/Shanghai]")

public class FavouritesCrudRequestBody   {
    @JsonProperty("newFavouritesName")
    private String newFavouritesName;

    @JsonProperty("existingFavouritesName")
    private String existingFavouritesName;

    public FavouritesCrudRequestBody newFavouritesName(String newFavouritesName) {
        this.newFavouritesName = newFavouritesName;
        return this;
    }

    /**
     * Get newFavouritesName
     * @return newFavouritesName
     */
    @ApiModelProperty(value = "")


    public String getNewFavouritesName() {
        return newFavouritesName;
    }

    public void setNewFavouritesName(String newFavouritesName) {
        this.newFavouritesName = newFavouritesName;
    }

    public FavouritesCrudRequestBody existingFavouritesName(String existingFavouritesName) {
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


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavouritesCrudRequestBody favouritesCrudRequestBody = (FavouritesCrudRequestBody) o;
        return Objects.equals(this.newFavouritesName, favouritesCrudRequestBody.newFavouritesName) &&
                Objects.equals(this.existingFavouritesName, favouritesCrudRequestBody.existingFavouritesName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newFavouritesName, existingFavouritesName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FavouritesCrudRequestBody {\n");

        sb.append("    newFavouritesName: ").append(toIndentedString(newFavouritesName)).append("\n");
        sb.append("    existingFavouritesName: ").append(toIndentedString(existingFavouritesName)).append("\n");
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

