package org.openapitools.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "scene_cover_image")
public class SceneCoverImage {// 场景封面表
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long imageId; // 图片id

    @Column(name = "url")
    private String url; // 图片地址

    @Column(name = "scene_id")
    private long sceneId; // 场景id

    @Column(name = "upload_date")
    private LocalDateTime uploadDate; // 图片上传的日期和时间，默认为当前时间

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int isDeleted; // 是否删除，0否1是
}