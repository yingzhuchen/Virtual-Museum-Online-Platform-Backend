package org.openapitools.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "homepage_carousel_images")
public class HomepageCarouselImages {//主页轮播图
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homepage_carousel_images_id")
    private Long homepageCarouselImagesId; // 主页轮播图id

    @Column(name = "scene_id")
    private Long sceneId; // 场景id

    @Column(name = "creator_id")
    private Long creatorId; // 创建者id

    @Column(name = "image_url")
    private String imageUrl; // 图片url

    @Column(name = "caption")
    private String caption; // 图片的描述说明

    @Column(name = "order_index")
    private Integer orderIndex; // 图片在轮播图中的显示顺序

    @Column(name = "created_time")
    private Date createdTime; // 创建时间的时间戳

    @Column(name = "is_deleted")
    private Integer isDeleted; // 创建时间的时间戳

}
