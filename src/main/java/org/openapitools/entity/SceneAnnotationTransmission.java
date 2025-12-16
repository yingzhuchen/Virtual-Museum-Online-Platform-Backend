package org.openapitools.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "scene_annotation_transmission")
public class SceneAnnotationTransmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annotation_transmit_id")    // 传送点id
    private long annotationTransmitId;

    @Column(name = "annotation_id")     // 标记id
    private long annotationId;

    @Column(name = "to_scene_id")    // 传送去场景的id
    private Long toSceneId;

    @Column(name = "to_transmission_annotation_id")    // 传送去场景的传送点的id
    private Long toTransmissionAnnotationId;

    @Column(name = "is_deleted")    // 是否删除
    private int isDeleted;

}
