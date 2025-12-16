package org.openapitools.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "scene_annotation_navigation")
public class SceneAnnotationNavigation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "navigation_id")    // 导航点id
    private long navigationId;

    @Column(name = "annotation_id")     // 标记id
    private long annotationId;

    @Column(name = "line_point_list", columnDefinition = "json")    // 折线中的点
    private String linePointList;



    @Column(name = "is_deleted")    // 是否删除
    private int isDeleted;

}
