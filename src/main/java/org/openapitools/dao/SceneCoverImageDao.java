package org.openapitools.dao;

import org.openapitools.entity.SceneCoverImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SceneCoverImageDao extends JpaRepository<SceneCoverImage,Long> {

    Optional<SceneCoverImage> findBySceneId(long sceneId);


}
