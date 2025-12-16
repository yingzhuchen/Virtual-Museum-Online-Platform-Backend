package org.openapitools.dao;

import org.openapitools.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TagsDao extends JpaRepository<Tags, Long> {

    public List<Tags> findByIsDeletedAndTagNameContaining(int isDeleted, String tagName);



    @Modifying
    @Query("UPDATE Tags t SET t.isDeleted = 1 WHERE t.tagId = :tagId")
    void softDeleteTag(@Param("tagId") Long tagId);
}
