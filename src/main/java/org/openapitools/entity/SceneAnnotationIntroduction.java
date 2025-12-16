package org.openapitools.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "scene_annotation_introduction")
public class SceneAnnotationIntroduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scene_annotation_introduction_id")
    private long sceneAnnotationIntroductionId;

    @Column(name = "annotation_id")
    private long annotationId;

    @Column(name = "brief_introduction")
    private String briefIntroduction;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "video")
    private String video;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "focal_point_view_x")
    private Double focalPointViewX; // 位置x

    @Column(name = "focal_point_view_y")
    private Double focalPointViewY; // 位置y

    @Column(name = "focal_point_view_z")
    private Double focalPointViewZ; // 位置z

    @Column(name = "is_deleted")
    private int isDeleted;  //是否删除，0否1是
}
