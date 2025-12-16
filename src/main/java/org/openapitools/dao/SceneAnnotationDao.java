package org.openapitools.dao;

import org.apache.ibatis.annotations.Param;
import org.openapitools.entity.SceneAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SceneAnnotationDao extends JpaRepository<SceneAnnotation, Long> {

//    Optional<SceneAnnotation> findByPositionXAndPositionYAndPositionZ(double positionX, double positionY, double positionZ);

    @Query("SELECT s FROM SceneAnnotation s WHERE s.positionX = :positionX AND s.positionY = :positionY AND s.positionZ = :positionZ AND s.isDeleted = 0")
    Optional<SceneAnnotation> findByPositionXAndPositionYAndPositionZ(@Param("positionX") double positionX, @Param("positionY") double positionY, @Param("positionZ") double positionZ);

    @Query("SELECT s FROM SceneAnnotation s WHERE s.sceneId = :sceneId AND s.isDeleted = 0 ORDER BY s.orderId")
    List<SceneAnnotation> findAllBySceneId(long sceneId);

    @Query("SELECT type FROM SceneAnnotation WHERE annotationId = :annotationId")
    String getAnnotationType(@Param("annotationId") long annotationId);
}
