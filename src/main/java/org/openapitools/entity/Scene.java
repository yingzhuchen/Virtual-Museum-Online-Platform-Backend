package org.openapitools.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.*;
import org.openapitools.model.Vector3;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "scene") // 映射到数据库的 Scene 表
public class Scene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Scene_Id")
    private long sceneId;

    @Column(name = "Scene_Creator_Id")
    private long sceneCreatorId;

    @Column(name = "Scene_Create_Time")
    private LocalDateTime sceneCreateTime;

    @Column(name = "Scene_Name")
    private String sceneName;

    @Column(name = "Scene_Url")
    private String sceneUrl;

    @Column(name = "Scene_Description")
    private String sceneDescription;

    @Column(name = "Collaborators", columnDefinition = "json")
    private String collaborators;

    @Column(name = "Tag_List", columnDefinition = "json")
    private String tags;

    @Column(name = "Scene_Is_Deleted", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int sceneIsDeleted;

    @Column(name = "Scene_Is_Audit", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int sceneIsAudit;

    @Column(name = "Scene_Is_Public", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int sceneIsPublic;

    @Column(name = "Scene_Cover_Url")
    private String sceneCoverUrl;

    @Column(name = "birth_Position")
    private String birthPosition;

    @Column(name = "birth_Rotation")
    private String birthRotation;

    @Column(name = "delete_time")
    private LocalDateTime deleteTime;

    @Column(name = "audio_url")
    private String audioUrl;



    public List<String> getTagsList() {
        List<String> tagList = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(tags);
            for (Object obj : jsonArray) {
                if (obj instanceof JSONObject) {
                    JSONObject jsonObj = (JSONObject) obj;
                    String tagName = jsonObj.getString("tag_name");
                    tagList.add(tagName);
                }
            }
        }
        return tagList;
    }

    public Vector3 getBirthPosition() {
        return birthPosition == null ? null : JSON.parseObject(birthPosition, Vector3.class);
    }

    public Vector3 getBirthRotation() {
        return birthRotation == null ? null : JSON.parseObject(birthRotation, Vector3.class);
    }


    public boolean isSceneAssist(long userId){
        String jsonString = this.getCollaborators();
        //对应字段为null时，表示没有协助者，因此userId不可能为该scene的协作者
        if(jsonString==null) return false;
        JSONArray collaborators = JSON.parseArray(jsonString);
        int i;
        for (i = 0; i < collaborators.size(); i++) {
            JSONObject collaborator = collaborators.getJSONObject(i);
            long id = collaborator.getLong("collaborator_id");
            if (id == userId) break;
        }
        return i != collaborators.size();
    }

}
