package org.openapitools.dao;

import org.apache.ibatis.annotations.Param;
import org.openapitools.entity.SceneAnnotationIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SceneAnnotationIntroductionDao extends JpaRepository<SceneAnnotationIntroduction, Long> {
    @Modifying
    @Query("UPDATE SceneAnnotationIntroduction SET isDeleted =1 WHERE annotationId = :annotationId")
    void updateByAnnotationId(@Param("annotationId") long annotationId);

    @Query("SELECT s FROM SceneAnnotationIntroduction s WHERE s.annotationId = :annotationId")
    SceneAnnotationIntroduction findByAnnotationId(@Param("annotationId") long annotationId);
}
