package org.openapitools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "esscene", type = "scene")
public class EsScene {

    private Integer id;

    private long userId;

    private String userName;

    private long sceneId;

    private long sceneCreatorId;

    private Date sceneCreateTime;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String sceneName;

    private String sceneUrl;

    @Field(store = true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String sceneDescription;

    private String collaborators;

    private int sceneIsPublic;

}
