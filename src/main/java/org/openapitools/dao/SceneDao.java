package org.openapitools.dao;

import org.apache.ibatis.annotations.ResultType;
import org.openapitools.entity.Scene;
import org.openapitools.model.EsScene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface SceneDao extends JpaRepository<Scene, Long> {

    Optional<Scene> findBySceneIdAndSceneIsDeleted(long sceneId,int isDeleted);
    default Optional<Scene> findBySceneId(long sceneId){
        return findBySceneIdAndSceneIsDeleted(sceneId,0);
    };
    @Query("SELECT s FROM Scene s WHERE s.sceneCreatorId = :creatorId and s.sceneIsDeleted = :isDeleted and s.deleteTime BETWEEN :startDate AND :endDate")
    List<Scene> findScenesDeletedInLast30Days(long creatorId,int isDeleted,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Modifying
    @Transactional
    @Query("UPDATE Scene s SET s.sceneIsDeleted = 1 WHERE s.sceneId = :sceneId")
    int softDeleteSceneBySceneId(@Param("sceneId") Long sceneId);

    @Modifying
    @Transactional
    @Query("UPDATE Scene s SET s.sceneIsDeleted = 0 WHERE s.sceneId = :sceneId")
    int undoDeleteSceneBySceneId(@Param("sceneId") Long sceneId);


    @Query("select s from Scene s where s.sceneCreatorId = :creatorId and s.sceneIsDeleted = :isDeleted")
    List<Scene> findAllByCreatorIdAndSceneIsDeleted(long creatorId,int isDeleted);

    List<Scene> findAllBySceneIsPublicAndSceneIsDeleted(int isPublic,int isDeleted);

    @Query("SELECT u.userId, u.userName, s.sceneId, s.sceneCreatorId, s.sceneCreateTime, s.sceneName,s.sceneUrl,s.sceneDescription,s.collaborators,s.sceneIsPublic " +
            "FROM User u " +
            "INNER JOIN Scene s ON u.userId = s.sceneCreatorId")
    @ResultType(EsScene.class)
    List<EsScene> fetchUserData();
    
    @Query(value = "SELECT * FROM scene WHERE JSON_CONTAINS(Collaborators, JSON_OBJECT('collaborator_id', :userId))"
            , nativeQuery = true)
    List<Scene> findAllByAssist(@Param("userId") long userId);

    List<Scene> findAllBySceneIsDeleted(int isDeleted);
}



