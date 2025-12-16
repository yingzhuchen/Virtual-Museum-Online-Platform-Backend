package org.openapitools.dao;

import org.apache.ibatis.annotations.Param;
import org.openapitools.entity.SceneAnnotationNavigation;
import org.openapitools.entity.SceneAnnotationTransmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SceneAnnotationTransmissionDao extends JpaRepository<SceneAnnotationTransmission, Long> {
    @Modifying
    @Query("UPDATE SceneAnnotationTransmission SET isDeleted =1 WHERE annotationId = :annotationId")
    void updateByAnnotationId(@Param("annotationId") long annotationId);

    @Query("SELECT s FROM SceneAnnotationTransmission s WHERE s.annotationId = :annotationId")
    SceneAnnotationTransmission findByAnnotationId(@Param("annotationId") long annotationId);


}