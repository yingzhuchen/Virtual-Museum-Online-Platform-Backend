package org.openapitools.dao;

import org.apache.ibatis.annotations.ResultType;
import org.openapitools.entity.Scene;
import org.openapitools.entity.SceneCollect;
import org.openapitools.entity.SceneHistory;
import org.openapitools.model.EsScene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface SceneCollectDao extends JpaRepository<SceneCollect, Long> {
}
