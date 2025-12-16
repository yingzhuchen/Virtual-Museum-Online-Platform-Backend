package org.openapitools.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.model.TopSceneId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "User_info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id") // 映射到数据库的 User_Id 列
    private long userId;

    @Column(name = "User_Avatar_Url") // 映射到数据库的 User_Avatar_Url 列
    private String AvatarUrl;

    @Column(name = "User_Name") // 映射到数据库的 User_Name 列
    private String userName;

    @Column(name = "User_Password") // 映射到数据库的 User_Password 列
    private String userPassword;

    @Column(name = "User_Version") // 映射到数据库的 User_Version 列
    private int userVersion = 0;

    @Column(name = "User_Is_Deleted") // 映射到数据库的 User_Is_Deleted 列
    private int userIsDeleted = 0;

    @Column(name = "top_scene_id", columnDefinition = "json") // 映射到数据库的 top_scene_id 列
    private String topSceneId ;

    public List<TopSceneId> getTopSceneId(){
        List<TopSceneId> topSceneIdList = new ArrayList<>();
        if (topSceneId != null && !topSceneId.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(topSceneId);
            for (Object obj : jsonArray) {
                if (obj instanceof JSONObject) {
                    JSONObject jsonObj = (JSONObject) obj;
                    int SceneId = jsonObj.getInteger("top_scene_id");
                    //模型转换
                    TopSceneId topSceneIds = new TopSceneId();
                    topSceneIds.setId(SceneId);

                    topSceneIdList.add(topSceneIds);
                }
            }
        }
        return topSceneIdList;
    }

}