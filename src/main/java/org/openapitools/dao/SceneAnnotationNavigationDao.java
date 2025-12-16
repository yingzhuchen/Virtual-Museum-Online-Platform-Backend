package org.openapitools.dao;

import org.apache.ibatis.annotations.Param;
import org.openapitools.entity.SceneAnnotationIntroduction;
import org.openapitools.entity.SceneAnnotationNavigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SceneAnnotationNavigationDao extends JpaRepository<SceneAnnotationNavigation, Long> {
    @Modifying
    @Query("UPDATE SceneAnnotationNavigation SET isDeleted =1 WHERE annotationId = :annotationId")
    void updateByAnnotationId(@Param("annotationId") long annotationId);

    @Query("SELECT s FROM SceneAnnotationNavigation s WHERE s.annotationId = :annotationId")
    SceneAnnotationNavigation findByAnnotationId(@Param("annotationId") long annotationId);
}
