package org.openapitools.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "scene_annotation")
public class SceneAnnotation {//标记表
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annotation_id")
    private long annotationId; // 标记表id

    @Column(name = "scene_id")
    private long sceneId; // 场景id

    @Column(name = "position_x")
    private double positionX; // 位置x

    @Column(name = "position_y")
    private double positionY; // 位置y

    @Column(name = "position_z")
    private double positionZ; // 位置z

    @Column(name = "name")
    private String name; // 名字

    @Column(name = "type")
    private String type; // 类型

    @Column(name = "color")
    private String color; // 颜色

    @Column(name = "radius")
    private double radius; // 半径

    @Column(name = "is_deleted")
    private int isDeleted;//是否删除，0否1是

    @Column(name = "order_id")
    private Long orderId;
}
